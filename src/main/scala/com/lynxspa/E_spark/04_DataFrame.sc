import com.lynxspa.E_spark.util.DummySparkObjects.{sc, spark}
import org.apache.spark.sql.{DataFrame, RelationalGroupedDataset, Row}
import org.apache.spark.sql
import spark.implicits._
import org.apache.spark.sql.types._

import java.util.Properties

// =========================================================== //
// ======================== DataFrame ======================== //
// =========================================================== //

case class Cliente(id: Int, eta: Int, nome: String)
val clientiCaseClass: List[Cliente] = List(
  Cliente(1, 41, "Mario Rossi"),
  Cliente(2, 24, "Luca Bianchi"),
  Cliente(3, 17, "Giulia Verdi"),
  Cliente(4, 23, "Francesca Romano"),
  Cliente(5, 12, "Roberto Esposito"),
  Cliente(6, 21, "Anna Conti"),
  Cliente(7, 24, "Lucia Colombo")
)

val clientiTupla: List[(Int, Int, String)] = List(
  (8, 32, "Giovanni Ferrari"),
  (9, 17, "Giuseppe Martini"),
  (10, 29, "Giulia Leone")
)

val ordini: List[(Int, Double)] = List(
  (1, 102.32),
  (2, 64.93),
  (3, 26.38),
  (4, 9.76),
  (5, 342.06),
  (11, 920.42)
)

val clientiRDD = sc.parallelize(clientiCaseClass)

val clientiDF: DataFrame = clientiCaseClass.toDF()
// creando un DataFrame da una collezione di tuple è consigliabile dichiarare il nome dei campi
// come default i campi saranno altrimenti quelli della tupla: _1, _2, _3
val clienti2DF: DataFrame = clientiTupla.toDF("id", "eta", "nome")
val ordiniDF: DataFrame = ordini.toDF("id", "importo")


/*** AZIONI ***/

/** show **/
println("clientiDF.show(3)")
clientiDF.show(3)


/** head **/
// ogni elemento di un DataFrame è un oggetto di tipo Row
val array: Array[Row] = clientiDF.head(n = 3)
//val array: Array[(Int, Int, String)] = clientiRDD.take(3)


/** first **/
val row: Row = clientiDF.first()


/** collect **/
clientiDF.collect()


/** take **/
clientiDF.take(n = 3)



/*** UTILITY ***/

/** printSchema **/
println("printSchema")
clientiDF.printSchema()

/** columns **/
val colonne: Array[String] = clientiDF.columns
println(s"colonne: ${colonne.mkString(",")}")



/*** TRASFORMAZIONI ***/

/** select **/
clientiDF.select($"nome")
// equivalente
clientiRDD.map {
  case Cliente(a, b, c) => c
}.count()


/** drop **/
clientiDF.drop($"id")
// equivalente
clientiRDD.map {
  case Cliente(a, b, c) => (b, c)
}.count()


/** filter / where **/
clientiDF.filter($"nome".startsWith("Luc"))
clientiDF.where("nome like 'Luc%'")
// equivalente
clientiRDD.filter {
  case Cliente(id, eta, nome) => nome.startsWith("Luc")
}.count()




/** withColumn **/
clientiDF.withColumn("maggiorenne", $"eta">=18)
// equivalente
println(clientiRDD.map {
  case Cliente(id, eta, nome) =>
    val maggiorenne = eta >= 18
    (id, eta, nome, maggiorenne)
}.count())


/** withColumnRenamed **/
clientiDF.withColumnRenamed("nome", "first name")


/** groupBy **/
val res01: DataFrame = clientiDF.withColumn("maggiorenne", $"eta">=18)
val res02: RelationalGroupedDataset = res01.groupBy("maggiorenne")
val res03: DataFrame = res02.agg(sql.functions.avg("eta")) // sum, max, eccetera
// equivalente
// N.B.: innescando l'esecuzione (count, collect, ecc) di questo blocco di codice
// in questa posizione ho incontrato degli errori interni di Spark (StackOverflow sul serializzatore)
// se si desiderasse eseguirlo consiglio di spostarlo in testa al file
clientiRDD.map {
  case cliente@Cliente(id, eta, nome) =>
    val maggiorenne = eta >= 18
    maggiorenne -> List(cliente)
}.reduceByKey {
  _ ++ _
}.mapValues {
  clienti => clienti.map(_.eta).sum.toFloat / clienti.length
}

/** orderBy / sort **/
clientiDF.orderBy("eta")
clientiDF.sort("eta")
clientiDF.sort($"eta".desc)
// equivalente
clientiRDD.sortBy(_.eta)


/** distinct / dropDuplicates **/
clientiDF.distinct()
clientiDF.dropDuplicates()
// l'unica differenza tra i due metodi
// è che dropDuplicate permette di specificare una colonna su effettuare il controllo di univocità
clientiDF.dropDuplicates("eta")
// equivalente
clientiRDD.distinct()


/** join **/
clientiDF.join(ordiniDF, "id")
clientiDF.join(ordiniDF, Seq("id"), "left")
clientiDF.join(ordiniDF, Seq("id"), "right")
clientiDF.join(ordiniDF, Seq("id"), "outer")
// equivalente
clientiRDD.keyBy(_.id).join(clientiRDD.keyBy(_.id))
clientiRDD.keyBy(_.id).leftOuterJoin(clientiRDD.keyBy(_.id))
clientiRDD.keyBy(_.id).rightOuterJoin(clientiRDD.keyBy(_.id))
clientiRDD.keyBy(_.id).fullOuterJoin(clientiRDD.keyBy(_.id))

/** union **/
clientiDF.union(clienti2DF)
// equivalente
clientiRDD.union(clientiRDD)



/*** I/O ***/

/** read da CSV **/
// 1,Wilone,Yakutin,42,F,wyakutin0@webs.com,130.12.221.64
val schemaUtente = StructType(Array(
  StructField("id", IntegerType, nullable = true),
  StructField("nome", StringType, true),
  StructField("cognome", StringType, true),
  StructField("eta", IntegerType, true),
  StructField("sesso", StringType, true),
  StructField("email", StringType, true),
  StructField("ip", StringType, true)
))

val utentiDF = spark.read
  .option("header", "false")
  .schema(schemaUtente)
  .csv("utenti.csv")

utentiDF.show(numRows = 10)

/** write a CSV **/
// N.B.: ogni partizione scriverà un file separato
utentiDF.select("nome", "ip")
  .write
  .mode("overwrite")
  .csv("/output/nomi_ip_df")


// Formati disponibili in lettura
// CSV
spark.read.csv("path")
spark.read.format("csv").load("path")


// Parquet:
spark.read.parquet("path")
spark.read.format("parquet").load("path")


// JSON:
spark.read.json("path")
spark.read.format("json").load("path")


// JDBC (database):
spark.read.jdbc("url", "tabella", new Properties)


// Delta Lake:
spark.read.format("delta").load("path")


// Avro (richiede una libreria aggiuntiva):
spark.read.format("avro").load("path")


// ORC (Optimized Row Columnar):
spark.read.orc("path")
spark.read.format("orc").load("path")


// TXT:
spark.read.text("path")
spark.read.format("text").load("path")


// Formati disponibili in scrittura (gli stessi)
// CSV:
val df = spark.emptyDataFrame
df.write.csv("path")
df.write.format("csv").save("path")

// Parquet:
df.write.parquet("path")
df.write.format("parquet").save("path")

// JSON:
df.write.json("path")
df.write.format("json").save("path")

// JDBC:
df.write.jdbc("url", "tabella", new Properties)

// Delta Lake:
df.write.format("delta").save("path")

// Avro:
df.write.format("avro").save("path")

// ORC:
df.write.orc("path")
df.write.format("orc").save("path")

// TXT:
df.write.text("path")
df.write.format("text").save("path")

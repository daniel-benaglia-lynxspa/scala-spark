import com.lynxspa.E_spark.util.DummySparkObjects.{sc, spark}
import org.apache.spark.sql

// rifare tutti gli esercizi precedenti, con i DataFrame :)

// Esempio esercizio_01
case class Cliente(id: Int, eta: Int, nome: String)
val clienti: List[Cliente] = List(
  Cliente(1, 41, "Mario Rossi"),
  Cliente(2, 24, "Luca Bianchi"),
  Cliente(3, 43, "Giulia Verdi"),
  Cliente(4, 23, "Francesca Romano"),
  Cliente(5, 61, "Roberto Esposito"),
  Cliente(6, 21, "Anna Conti"),
  Cliente(7, 38, "Marco Ricci"),
  Cliente(8, 58, "Sofia Marini"),
  Cliente(9, 54, "Antonio Costa"),
  Cliente(10, 23, "Maria Rizzo")
)

import spark.implicits._
val clientiDF = clienti.toDF()

// Trovare la media dell'età dei clienti
clientiDF.agg(sql.functions.avg("eta")).show()

// Trovare i clienti la cui età è >= 30
clientiDF.filter($"eta" >= 30).show()
clientiDF.filter("eta >= 30").show()

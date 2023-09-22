import com.lynxspa.E_spark.util.DummySparkObjects.{sc, spark}
import org.apache.spark.rdd.RDD

import java.sql.Timestamp

case class Cliente(id: Int, eta: Int, nome: String)
case class Ordine(id: Int, idCliente: Int, data: Timestamp, importo: BigDecimal)

val clienti = List(
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

val ordini = List(
  Ordine(1, 1, Timestamp.valueOf("2023-09-01 10:23:00"), BigDecimal("21.34")),
  Ordine(2, 3, Timestamp.valueOf("2023-09-02 11:47:00"), BigDecimal("33.50")),
  Ordine(3, 2, Timestamp.valueOf("2023-09-03 14:02:00"), BigDecimal("17.89")),
  Ordine(4, 4, Timestamp.valueOf("2023-09-04 09:15:00"), BigDecimal("46.23")),
  Ordine(5, 6, Timestamp.valueOf("2023-09-05 12:35:00"), BigDecimal("52.75")),
  Ordine(6, 7, Timestamp.valueOf("2023-09-06 08:20:00"), BigDecimal("29.99")),
  Ordine(7, 8, Timestamp.valueOf("2023-09-07 16:54:00"), BigDecimal("15.67")),
  Ordine(8, 9, Timestamp.valueOf("2023-09-08 11:11:00"), BigDecimal("44.44")),
  Ordine(9, 10, Timestamp.valueOf("2023-09-09 13:26:00"), BigDecimal("38.90")),
  Ordine(10, 1, Timestamp.valueOf("2023-09-10 15:47:00"), BigDecimal("25.00")),
  Ordine(11, 9, Timestamp.valueOf("2023-09-11 10:15:00"), BigDecimal("32.53")),
  Ordine(12, 2, Timestamp.valueOf("2023-09-12 12:45:00"), BigDecimal("28.40")),
  Ordine(13, 4, Timestamp.valueOf("2023-09-13 09:30:00"), BigDecimal("19.75")),
  Ordine(14, 3, Timestamp.valueOf("2023-09-14 14:20:00"), BigDecimal("41.22")),
  Ordine(15, 2, Timestamp.valueOf("2023-09-15 16:33:00"), BigDecimal("37.89")),
  Ordine(16, 10, Timestamp.valueOf("2023-09-16 10:55:00"), BigDecimal("24.16")),
  Ordine(17, 3, Timestamp.valueOf("2023-09-17 13:10:00"), BigDecimal("50.32")),
  Ordine(18, 4, Timestamp.valueOf("2023-09-18 11:25:00"), BigDecimal("45.67")),
  Ordine(19, 7, Timestamp.valueOf("2023-09-19 15:02:00"), BigDecimal("31.25")),
  Ordine(20, 1, Timestamp.valueOf("2023-09-20 12:17:00"), BigDecimal("29.84"))
)

val clientiRDD: RDD[Cliente] = spark.sparkContext.parallelize(clienti)
val ordiniRDD: RDD[Ordine] = spark.sparkContext.parallelize(clienti)


// Creare un RDD in cui ho, per ogni elemento, un cliente e la somma degli importi degli ordini effettuati

// Creare un RDD in cui ho, per ogni elemento, un cliente e la data del primo ordine effettuato
import com.lynxspa.E_spark.util.DummySparkObjects.{sc, spark}

import java.sql.Timestamp

case class PrezzoAzione(timestamp: Timestamp, sigla: String, prezzo: Double)

val prezzi = List(
  PrezzoAzione(Timestamp.valueOf("2023-09-01 10:00:00"), "AAPL", 145.64),
  PrezzoAzione(Timestamp.valueOf("2023-09-01 11:00:00"), "AAPL", 147.23),
  PrezzoAzione(Timestamp.valueOf("2023-09-01 12:00:00"), "AAPL", 149.80),
  PrezzoAzione(Timestamp.valueOf("2023-09-01 13:00:00"), "AAPL", 152.14),
  PrezzoAzione(Timestamp.valueOf("2023-09-01 14:00:00"), "AAPL", 150.25),
  PrezzoAzione(Timestamp.valueOf("2023-09-01 15:00:00"), "AAPL", 153.65),
  PrezzoAzione(Timestamp.valueOf("2023-09-01 16:00:00"), "AAPL", 148.94),
  PrezzoAzione(Timestamp.valueOf("2023-09-01 17:00:00"), "AAPL", 147.20),
  PrezzoAzione(Timestamp.valueOf("2023-09-01 18:00:00"), "AAPL", 149.50),
  PrezzoAzione(Timestamp.valueOf("2023-09-01 19:00:00"), "AAPL", 151.24)
)

val prezziRDD = sc.parallelize(prezzi)

// Scrivere un metodo che trova la media del prezzo tra due timestamp

def media(t0: Timestamp, t1: Timestamp): Double = ???

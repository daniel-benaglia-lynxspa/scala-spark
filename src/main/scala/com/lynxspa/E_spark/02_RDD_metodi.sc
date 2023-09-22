import com.lynxspa.E_spark.util.DummySparkObjects.{sc, spark}
import org.apache.spark.rdd.RDD

// ========================================================== //
// ========================== RDD =========================== //
// ========================================================== //

val rdd: RDD[String] = sc.textFile("utenti.csv").repartition(sc.defaultParallelism)

case class Utente(id: Long,
                  nome: String,
                  cognome: String,
                  eta: Int,
                  sesso: String,
                  email: String,
                  ip: String)

val utenti: RDD[Utente] = rdd.map {
  riga => riga.split(",") match {
    case Array(id, nome, cognome, eta, sesso, email, ip) =>
      Utente(id.toLong, nome, cognome, eta.toInt, sesso, email, ip)
  }
}

def ordiniUtente(id: Long): List[String] = {
  List("ordine 1", "ordine 2")
}

/**
 * Dimostrazione metodi Spark che funzionano esattamente come i corrispondenti metodi Scala
 */

// *** map ***
utenti.map { utente =>
  (utente.id, utente.nome)
}


// *** flatMap ***
utenti.flatMap { utente =>
  ordiniUtente(utente.id).map { ordine =>
    (utente.id, utente.nome, ordine)
  }
}.take(10).foreach(println)



// *** flatMap con for-comprehension ***
for {
  utente <- utenti
  ordine <- ordiniUtente(utente.id)
} yield (utente.id, utente.nome, ordine)




// *** filter ***
val utentiMaggiorenni = utenti.filter { utente =>
  utente.eta >= 18
}



// *** reduce ***
val utentePiuVecchio: Utente = utenti.reduce { (a, b) =>
  if (a.eta > b.eta) a else b
}



// *** fold ***
val numeri = sc.parallelize(List(1,2,3,4,5))
// Questo utilizzo di fold garantisce un risultato, visto che viene fornito l'elemento di partenza (0).
// Utilizzando un semplice "reduce", l'algoritmo fallirebbe su un RDD vuoto.
val somma = numeri.fold(0) { (a, b) =>
  a + b
}



// *** collect ***
val emailUtentiMaggiorenni = utenti.collect {
  case utente if utente.eta >= 18 => utente.email
}


// N.B.: collect senza argomenti è un metodo completamente diverso!
// Viene utilizzato per portare tutti i dati sul driver, ottenendo un Array (invece di un RDD)
// Questa operazione è da evitare su RDD di grosse dimensioni,
// in quanto stiamo portando tutti i dati attualmente distribuiti sul cluster su di una singola macchina (il driver)
val utentiArray: Array[Utente] = utenti.collect()
val takeRes: Array[Utente] = utenti.take(utenti.count().toInt)



// *** count ***
// count equivale a "size" o "length" in scala
val numeroUtenti = emailUtentiMaggiorenni.count()

import com.lynxspa.E_spark.util.DummySparkObjects.{sc, spark}
import org.apache.spark.rdd.RDD

import java.time.LocalDate

// ========================================================== //
// ======================== PairRDD ========================= //
// ========================================================== //

val rddRigheClienti: RDD[String] = sc.textFile("utenti.csv").repartition(sc.defaultParallelism)
val rddRigheOrdini: RDD[String] = sc.textFile("ordini.csv").repartition(sc.defaultParallelism)

case class Utente(id: Long,
                  nome: String,
                  cognome: String,
                  eta: Int,
                  sesso: String,
                  email: String,
                  ip: String)

case class Ordine(id: Long,
                  idCliente: Long,
                  data: LocalDate,
                  importo: BigDecimal)

val utenti: RDD[Utente] = rddRigheClienti.map {
  riga => riga.split(",") match {
    case Array(id, nome, cognome, eta, sesso, email, ip) =>
      Utente(id.toLong, nome, cognome, eta.toInt, sesso, email, ip)
  }
}

val ordini: RDD[Ordine] = rddRigheOrdini.map {
  riga => riga.split(",") match {
    case Array(id, idCliente, data, importo) =>
      Ordine(id.toLong, idCliente.toLong, LocalDate.parse(data), BigDecimal(importo))
  }
}


/**
 * Dimostrazione metodi Spark su PairRDDFunctions.
 * PairRDDFunctions è una classe implicita definita come un RDD di tupla 2.
 *
 * N.B.: PariRDD non è propriamente una classe implicita, la classe in sé è una classe normale,
 * e viene utilizzato un metodo implicito per fornire la conversione da RDD a PairRDD,
 * il metodo è:
 *
 * implicit def rddToPairRDDFunctions[K, V](rdd: RDD[(K, V)])
 * (implicit kt: ClassTag[K], vt: ClassTag[V], ord: Ordering[K] = null): PairRDDFunctions[K, V] = {
 *    new PairRDDFunctions(rdd)
 * }
 */

/**
 * Per creare un RDD di tupla 2 si può utilizzare keyBy
 * Le prossime 2 righe sono equivalenti.
 */
val pairRDDUtenti: RDD[(Long, Utente)] = utenti.keyBy(utente => utente.id)
// La riga precedente equivale a utenti.map(utente => utente.id -> utente)

val pairRDDOrdini: RDD[(Long, Ordine)] = ordini.keyBy(ordine => ordine.idCliente)

// *** values ***
// values ci restituisce il secondo elemento di ogni tupla, ovvero il "valore" nella coppia "chiave-valore"
val res: RDD[Ordine] = pairRDDOrdini.values
pairRDDOrdini.map(tupla => tupla._2)


// *** keys ***
// keys ci restituisce il primo elemento di ogni tupla, ovvero la "chiave" nella coppia "chiave-valore"
pairRDDOrdini.keys


// *** mapValues ***
// mapValues agisce mappando solo il secondo elemento di ogni tupla, ovvero il "valore" nella coppia "chiave-valore"
// la "chiave" di ogni elemento rimane invariata
// le 3 righe che seguono sono equivalenti
pairRDDOrdini.mapValues(ordine => ordine.data)
pairRDDOrdini.map { case (idCliente, ordine) => (idCliente, ordine.data) }
pairRDDOrdini.map(tupla => (tupla._1, tupla._2.data))


// *** reduceByKey ***
// reduceByKey applica l'algoritmo del "reduce" per ogni elemento con chiave uguale,
// fornendo così un singolo elemento per ogni chiave
// un utilizzo tipico è l'aggregazione in una Lista di tutti gli elementi con la stessa chiave
val ordiniPerIdCliente = ordini.map {
  // questo step crea una lista con un singolo ordine per ogni elemento dell'RDD
  // questa lista sarà il "valore" di una Tupla2 la cui "chiave" sarà l'idCliente
  ordine => ordine.idCliente -> List(ordine)
}.reduceByKey {
  // questo step riduce tutti i record con lo stesso idCliente in uno singolo
  // l'operazione di aggregazione è la concatenazione delle liste
  (listaA, listaB) => listaA ++ listaB
}

ordiniPerIdCliente.take(25).foreach {
  case (idCliente, ordini) =>
//    println(s"Il cliente con id $idCliente ha effettuato i seguenti ordini: $ordini")
}

// *** join ***
// join unisce 2 RDD convertibili a PairRDDFunctions in un singolo RDD, unendo i record per chiave
// (il primo elemento della tupla 2)
pairRDDUtenti.join(ordiniPerIdCliente).foreach {
  case (idCliente, (cliente, ordini)) =>
    // Ogni riga dell'RDD risultante è a sua volta una Tupla2, in cui la chiave è sempre l'idCliente
    // e il valore è ancora una volta una Tupla2 con il cliente e la lista di ordini (i valori dei 2 RDD originali)
    println(s"Il cliente con id $idCliente = $cliente. Ha effettuato i seguenti ordini: $ordini")
}


// *** lefOuterJoin ***
// leftOuterJoin garantisce la presenza nell'RDD finale di tutti gli elementi dell'RDD "sinistro"
// l'RDD sinistro è quello su cui viene chiamato il metodo, l'RDD destro è quello che viene passato come argomento
pairRDDUtenti.leftOuterJoin(ordiniPerIdCliente).foreach {
  case (idCliente, (cliente, forseOrdini)) =>
    // utilizzando leftOuterJoin gli elementi corrispondenti all'RDD destro si trovano ora in un Option
    // questo perchè non è garantito che le chiavi tra i due RDD corrispondano 1:1
    // nell'esempio precedente di "join" i record senza corrispondenza andavano persi,
    // mentre ora sono garantiti tutti quelli dell'RDD sinistro
    forseOrdini match {
      case Some(ordini) => println(s"Il cliente con id $idCliente = $cliente. Ha effettuato i seguenti ordini: $ordini")
      case None => println(s"Il cliente con id $idCliente = $cliente. Non ha effettuato ordini.")
    }

}



// *** rightOuterJoin ***
// leftOuterJoin garantisce la presenza nell'RDD finale di tutti gli elementi dell'RDD "destro"
pairRDDUtenti.rightOuterJoin(ordiniPerIdCliente).foreach {
  case (idCliente, (forseCliente, ordini)) =>
    // utilizzando rightOuterJoin gli elementi corrispondenti all'RDD sinistro si trovano ora in un Option
    forseCliente match {
      case Some(cliente) => println(s"Il cliente con id $idCliente = $cliente. Ha effettuato i seguenti ordini: $ordini")
      case None => println(s"Il cliente con id $idCliente non esiste. Nonostante ciò abbiamo i seguenti ordini associati: $ordini")
    }
}



// *** fullOuterJoin ***
// fullOuterJoin garantisce la presenza nell'RDD finale di tutti gli elementi di entrambi gli RDD di partenza
pairRDDUtenti.fullOuterJoin(ordiniPerIdCliente).foreach {
  case (idCliente, (forseCliente, forseOrdini)) =>
    // utilizzando fullOuterJoin tutti gli elementi corrispondenti agli RDD iniziali si trovano ora in in Option
    forseCliente match {

      case Some(cliente) =>
        forseOrdini match {
          case Some(ordini) => println(s"Il cliente con id $idCliente = $cliente. Ha effettuato i seguenti ordini: $ordini")
          case None => println(s"Il cliente con id $idCliente = $cliente. Non ha effettuato ordini.")
        }


      case None =>
        forseOrdini match {
          case Some(ordini) => println(s"Il cliente con id $idCliente non esiste. Nonostante ciò abbiamo i seguenti ordini associati: $ordini")
          case None =>
          // Questo scenario non può verificarsi, almeno uno dei due Option deve essere valorizzato,
          // altrimenti quella chiave non esisterebbe proprio, deve essere "nata" associata almeno ad un Cliente o ad un Ordine
        }
    }
}
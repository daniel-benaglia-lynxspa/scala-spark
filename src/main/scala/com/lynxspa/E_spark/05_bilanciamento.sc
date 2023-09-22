import com.lynxspa.E_spark.util.DummySparkObjects.{sc, spark}

import scala.util.Random

// ============================================================ //
// ============== Partizionatori e Bilanciamento ============== //
// ============================================================ //
// Notare come risulta partizionato l'RDD prima e dopo il repartition


// Definizione di un partizionatore custom
class PartizionatoreCustomSbilanciato(partitions: Int) extends org.apache.spark.Partitioner {

  override def numPartitions: Int = partitions

  override def getPartition(key: Any): Int = {
    key match {
      // Le chiavi divisibili per 10 sulla prima partizione
      case k: Int if k % 10 == 0 => 0
      // Le chiavi divisibili per 4 sulla seconda partizione
      case k: Int if k % 4 == 0 => 1
      // Il resto random sulle partizioni rimanenti
      case _ => Random.nextInt(partitions - 2) + 2
    }
  }
}

val numPartitions = sc.defaultParallelism

val dati = (1 to 10000).map(x => (x,x))

// Utilizzo partizionatore custom per sbilanciare i dati sull'RDD, le prime due partizioni avranno più dati delle altre
val rddSbilanciato = sc.parallelize(dati).partitionBy(new PartizionatoreCustomSbilanciato(numPartitions))
println("====== BEFORE ======")
rddSbilanciato
  .glom() // Il metodo glom() ritorna un RDD di partizioni, ogni partizione è rappresentata da un Array
  .map(_.length) // ci interessa solo la lunghezza di ogni partizione
  .foreach { dimensione =>
    // stringa * N ripete la stringa N volte
    println(s"dimensione partizione: $dimensione ${"|" * (dimensione / 10)}>")
  }


// Ribilanciamento RDD tramite "repartition"
val rddBilanciato = rddSbilanciato.repartition(numPartitions)
println("====== AFTER ======")
rddBilanciato
  .glom // glom può essere chiamato senza parentesi visto che non richiede argomenti
  .map(_.length)
  .foreach { dimensione =>
    println(s"dimensione partizione: $dimensione ${"|" * (dimensione / 10)}>")
}

import com.lynxspa.E_spark.util.DummySparkObjects.{sc, spark}
import org.apache.spark.rdd.RDD

// ========================================================== //
// ========================== ETL =========================== //
// ================ Extract, Transform, Load ================ //
// ========================================================== //

/**
 * Lettura di un file CSV
 * tramite sc.textFile
 * che ritorna un RDD[String]
 *
 * Il parsing del CSV deve avvenire manualmente
 *
 * Esempio di una riga
 * 1,Wilone,Yakutin,42,F,wyakutin0@webs.com,130.12.221.64
 */
case class Utente(id: Long,
                  nome: String,
                  cognome: String,
                  eta: Int,
                  sesso: String,
                  email: String,
                  ip: String)

/**
 * Lettura file e ripartizionamento (vedi 05_bilanciamento.sc)
 * di default sc.textFile produce solo 2 partizioni
 */
val rdd: RDD[String] = sc.textFile("utenti.csv").repartition(sc.defaultParallelism)


/**
 * Parsing manuale del file splittando ogni riga sulla virgola
 */
val utenti: RDD[Utente] = rdd.map { riga =>
  riga.split(",") match {
    /**
     * Pattern matching sull'Array risultante dal metodo split
     */
    case Array(id, nome, cognome, eta, sesso, email, ip) =>
      Utente(id.toLong, nome, cognome, eta.toInt, sesso, email, ip)
  }
}

/**
 * Dimostrazione dei dati presenti su ogni partizione dell'RDD
 */
utenti.foreachPartition {
  part => println(s"Partizione: ${part.map(_.id).toList}")
}

/**
 * Mapping per la creazione di un nuovo CSV solamente con
 * nome,ip
 */
val nomeIp = utenti.map {
  utente => s"${utente.nome},${utente.ip}"
}

/**
 * Stampa a video dei primi 10 elementi del nuovo RDD
 */
nomeIp.take(10).foreach(println)

/**
 * Scrittura standard che risulterà divisa in 1 file per ogni partizione
 */
nomeIp.saveAsTextFile("/output/nomi_ed_ip")


/**
 * Scrittura dopo coalesce(1)
 * che risulterà in un unico file
 *
 * "coalesce" è un metodo simile a "repartition" per ripartizionare l'RDD su un diverso numero di partizioni
 * "coalesce" è usato per ridurre il numero di partizioni (ed è ottimizzato per questo utilizzo)
 * "repartition" può sia aumentare che diminuire
 */
nomeIp.coalesce(1).saveAsTextFile("/output/nomi_ed_ip_singolo")

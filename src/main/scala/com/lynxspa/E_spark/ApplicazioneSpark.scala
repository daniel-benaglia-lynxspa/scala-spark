package com.lynxspa.E_spark

import org.apache.log4j.Logger
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

// ============================================================== //
// ============== Classe Buildabile e Deployabile =============== //
// ==================== (copia di 01_ETL.sc) ==================== //

// Per buildare: mvn clean package
object ApplicazioneSpark {

  private val logger = Logger.getLogger("scala-spark")
  def main(args: Array[String]): Unit = {

    logger.info("inizio SparkMain01...")

    val spark: SparkSession = SparkSession.builder().getOrCreate()

    val sc: SparkContext = spark.sparkContext
    case class Utente(id: Long,
                      nome: String,
                      cognome: String,
                      eta: Int,
                      sesso: String,
                      email: String,
                      ip: String)

    /**
     * Lettura file e ripartizionamento
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
     * Mapping per la creazione di un nuovo CSV solamente con
     * nome,ip
     */
    val nomeIp = utenti.map {
      utente => s"${utente.nome},${utente.ip}"
    }

    /**
     * Stampa a video dei primi 10 elementi del nuovo RDD
     */
    nomeIp.take(10).foreach(logger.info)


    /**
     * Scrittura standard che risulterà divisa in 1 file per ogni partizione
     */
    nomeIp.saveAsTextFile("/output/nomi_ed_ip")

    logger.info("stop SparkMain01...")
    spark.stop()
  }

}

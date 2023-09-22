package com.lynxspa.E_spark.util

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
 * Questo oggetto esiste solamente con lo scopo di far funzionare correttamente gli aiuti dell'IDE
 * durante la scrittura del codice negli script Spark
 *
 * Gli oggetti sc e spark vengono forniti automaticamente durante l'esecuzione di una spark-shell.
 */
object DummySparkObjects {
  
  val sc: SparkContext = ???
  val spark: SparkSession = ???

}

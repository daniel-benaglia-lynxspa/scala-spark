import com.lynxspa.E_spark.util.DummySparkObjects.{sc, spark}
import org.apache.spark.rdd.RDD

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

val clientiRDD: RDD[Cliente] = sc.parallelize(clienti)

// Trovare la media dell'età dei clienti

// Trovare i clienti la cui età è >= 30
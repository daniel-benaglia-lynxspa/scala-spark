// Incapsulamento

// colore è dichiarato privato, incapsulandolo, come in Java
// per accedervi è necessario fornire un metodo: getColore
abstract class Figura(private val colore: String) {
  def getArea: Double
  def getColore: String = colore
}

class Quadrato(colore: String,
               val lato: Double) extends Figura(colore) {
  override def getArea: Double = lato * lato
}

val q = new Quadrato("rosso", 3)

println(s"Figura di colore ${q.getColore} con area ${q.getArea}")

//println(s"Figura di colore ${q.colore} con area ${q.getArea}") <- q.colore non è possibile, "colore" è privato


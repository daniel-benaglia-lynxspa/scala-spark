// Dichiarare un trait "Figura" con un metodo "area"
trait Figura {
  def area: Double
}
// Dichiarare le seguenti implementazioni:
// - Quadrato
// - Rettangolo
// - Cerchio (pi*r^2)
case class Quadrato(lato: Double) extends Figura {
  override def area = lato * lato
}
case class Rettangolo(a: Double, b: Double) extends Figura {
  override def area = a * b
}
case class Cerchio(r: Double) extends Figura {
  override def area = Math.PI * r * r
}
// Dichiarare una lista contenente
// 2 quadrati, 2 rettangoli, 2 cerchi
val lista = List(
  Quadrato(2),
  Quadrato(3),
  Rettangolo(3, 3),
  Rettangolo(1, 9),
  Cerchio(2),
  Cerchio(5)
)

// Trovare la somma dei perimetri dei rettangoli
lista.collect {
  case Rettangolo(a, b) => a + a + b + b
}.sum



/*
// Trovare la somma delle aree dei cerchi






// Dichiarare una case class Area con 1 campo:
// valore: Double






// Produrre una classe di tipo Area
// con il valore pari alla somma delle aree
// delle figure nella lista
*/


// Sealed Trait e Tipi Algebrici

// Tipo Prodotto
// I Tipi Prodotto sono tipi composti da altri tipi
// In questo esempio il tipo Prodotto Persona è il prodotto di nome per età
case class Persona(nome: String, eta: Int)


// Tipo Somma (unioni disgiunte)
// sono un tipo che può essere una di diverse varianti (in questo caso Figura può essere Quadrato o Rettangolo)

// N.B.:
// la parola chiave "sealed" obbliga tutte le implementazioni ad essere definite nello stesso file,
// questo ci permette di sapere che il tipo somma Figura è composto solo da Quadrato e Rettangolo
trait Figura {
  def area: Double
}
case class Quadrato(lato: Double) extends Figura {
  override def area: Double = lato * lato
}
case class Rettangolo(lunghezza: Double, larghezza: Double) extends Figura {
  override def area: Double = lunghezza * larghezza
}
// N.B.: Rettangolo è un Tipo Somma + Prodotto perché è sia un prodotto di lunghezza e larghezza
// che un tipo Somma di tipo Figura

val listaDiFigure: List[Figura] = List(
  Quadrato(3),
  Rettangolo(2, 5),
  Quadrato(4),
  Rettangolo(1, 3)
)

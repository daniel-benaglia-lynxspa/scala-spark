// Ereditarietà e Polimorfismo

// classe astratta dichiarata con la parola chiave "abstract"
abstract class Figura(val colore: String) {

  // I metodi astratti non devono essere esplicitati tali,
  // si capisce che sono astratti dal fatto che manca l'implementazione
  def getArea: Double
}

// Quando si estende una classe,
// i campi che sono già stati dichiarati dalla superclasse non vengono nuovamente "dichiarati"
// quindi in questo caso "colore" non viene nuovamente dichiarato "val colore (o var colore)"
// Mentre i nuovi campi, come "lato" vengono dichiarati ex novo usando val o var
class Quadrato(colore: String,
               val lato: Double) extends Figura(colore) { // Figura(colore) equivale a chiamare il costruttore "super"

  // Implementazione del metodo dichiarato astratto nella superclasse
  override def getArea: Double = lato * lato
}

class Rettangolo(colore: String,
                 val lunghezza: Double,
                 val larghezza: Double) extends Figura(colore) {
  override def getArea: Double = lunghezza * larghezza
}

val listaDiFigure: List[Figura] = List(
  new Quadrato("rosso", 3),
  new Rettangolo("blu", 2, 5.5)
)

for (figura <- listaDiFigure) {
  println(s"Figura di colore ${figura.colore}")
}
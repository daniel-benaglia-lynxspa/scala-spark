import java.util.Objects
// Case Class
case class QuadratoCaseClass(lato: Double)

// I campi di una case class sono di default pubblici e immutabili

// Dichiarando una case class vengono implementati automaticamente i seguenti metodi:
// - equals
// - hashCode
// - toString
// - copy
// viene creato anche il companion object con apply e unapply
// (unapply è utilizzato durante il pattern matching)

// Il metodo copy serve per creare una case class con dei campi modificati
// I campi non possono essere modificati sulla case class originale in quanto immutabili
// utilizzo:
val q = QuadratoCaseClass(3)
val latoQuattro = q.copy(lato = 4)



// Di seguito una dichiarazione "manuale" di tutti i campi che vengono implementati automaticamente su di una case class
class Quadrato(val lato: Double) {

  override def hashCode(): Int = {
    Objects.hashCode(this)
  }

  override def equals(obj: Any): Boolean = obj match {
    case that: Quadrato => java.lang.Double.compare(that.lato, lato) == 0
    case _ => false
  }

  override def toString: String = s"Quadrato($lato)"

  def copy(lato: Double): Quadrato = new Quadrato(lato)

}

object Quadrato {

  def apply(lato: Double): Quadrato = new Quadrato(lato)

  def unapply(quadrato: Quadrato): Option[Double] = Some(quadrato.lato)

}


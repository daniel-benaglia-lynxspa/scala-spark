// Tipi Generici

// List<String> -> Java
// List[String] -> Scala

// Come in Java, anche Scala permette la dichiarazione di tipi generici, la differenza sostanziale è una di sintassi:
// In Java si utilizza <T>, mentre in Scala si dichiara [T]
// In Java si dichiara prima il tipo, in Scala dopo, per questo in Scala la dichiarazione del tipo generico viene dopo
// Java: class <T> NomeClasse
// Scala: class NomeClasse [T]
class Contenitore[T](val contenuto: T) {
  def descriviContenuto: String = {
    s"il contenuto = $contenuto"
  }
}

val c = new Contenitore[String]("stringa")
val d = new Contenitore("stringa")

c.descriviContenuto

import scala.annotation.tailrec
// Case head tail

val lista = List(0, 1, 2, 3, 4, 5, 6, 7)

// E' possibile utilizzare il pattern matching ::
// per suddividere i singoli elementi di una lista
// (il primo solitamente chiamato "head") dal resto della lista (tail)
// N.B.: Nil corrisponde alla lista vuota
// quindi matchando sia head :: tail che Nil garantiamo sempre un match (tail può essere una lista vuota)
@tailrec
def stampaQuadrato(l: List[Int]): Unit = {
  l match {
    case head :: tail =>
      println(head * head, s"rimanente=$tail")
      stampaQuadrato(tail)
    case Nil => println("fine")
  }
}

stampaQuadrato(lista)

// Possiamo matchare tutti gli elementi che vogliamo, l'ultimo sarà il resto della lista
lista match {
  case primo :: secondo :: terzo :: rimanente =>
    println(s"primo $primo")
    println(s"secondo $secondo")
    println(s"terzo $terzo")
    println(s"rimanente $rimanente")
}

// E' possibile anche matchare sugli elementi in coda
lista match {
  case elementi :+ ultimo =>
    println(s"elementi $elementi")
    println(s"ultimo $ultimo")
}

lista match {
  case elementi :+ penultimo :+ ultimo =>
    println(s"elementi $elementi")
    println(s"penultimo $penultimo")
    println(s"ultimo $ultimo")
}
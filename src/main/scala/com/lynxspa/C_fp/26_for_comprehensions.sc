// For comprehensions

// I metodi foreach, map e flatMap
// possono essere scritti utilizzando una sintassi alternativa
// senza chiamare esplicitamente il metodo
val lista = List(1, 2, 3, 4)

// foreach
lista.foreach(println)
for (n <- lista) {
  println(n)
}

// map
lista.map(_ * 2)
for {
  n <- lista
} yield n * 2


// flatMap
lista.flatMap { n =>
  Range(0, n)
}
for {
  n <- lista // la prima riga chiama flatMap
  valore <- Range(0, n) // la seconda riga chiama map
} yield valore

// Esempio più esplicito sul fatto che la seconda riga è un map
lista.flatMap { n =>
  Range(0, n).map(valore => valore * 2)
}
for {
  n <- lista
  valore <- Range(0, n) // la seconda riga richiama map
} yield valore * 2


// La sintassi alternativa funziona su qualsiasi classe che definisce i metodi
// foreach, map e flatMap
// Provare a commentare una delle implementazioni
// e notare come la sintassi alternativa corrispondente non compila più
case class UnaClasse[T](valore: T) {
  def foreach(f: T => Unit): Unit = f(valore)
  def map(f: T => T): UnaClasse[T] = UnaClasse(f(this.valore))
  def flatMap[K](f: T => UnaClasse[K]): UnaClasse[K] = f(this.valore)
}
val unaClasse = UnaClasse(42)

// foreach
for {
  valore <- unaClasse
} println(valore)

// map
for {
  valore <- unaClasse
} yield valore

// flatMap
for {
  valore <- unaClasse
  elemento <- UnaClasse(valore)
} yield elemento * 2


// Esempio con più flatMap annidati
lista.flatMap { n =>
  Range(0, n).flatMap { valore =>
    List(1, 2, valore).map(valore2 => valore2 * 2)
  }
}
for {
  n <- lista // la prima riga chiama flatMap
  valore <- Range(0, n) // la seconda riga flatMap
  valore2 <- List(1, 2, valore) // l'ultima riga chiama sempre map
} yield valore2 * 2
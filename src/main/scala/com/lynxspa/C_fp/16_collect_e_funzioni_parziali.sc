// Collect e funzioni parziali

val lista = List(-3, -2, -1, 0, 1, 2, 3)


// Una funzione parziale è una funzione che ha un dominio limitato
// Non è quindi definita per tutti i possibili input
val funzioneParziale: PartialFunction[Int, Int] = new PartialFunction[Int, Int] {
  override def isDefinedAt(x: Int): Boolean = x > 0
  override def apply(x: Int): Int = x * x
}

// Il metodo "collect" accetta una funzione parziale
// e funziona come il filter + map dichiarato sotto
lista.collect(funzioneParziale)
lista.filter {
  x => funzioneParziale.isDefinedAt(x)
}.map {
  x => funzioneParziale.apply(x)
}

// Non è necessario creare una funzione parziale in maniera così verbosa
// si può utilizzare la sintassi con "case" e dichiararla direttamente inline
lista.collect {
  case x if x > 0 => x * x
}

// La parte "isDefinedAt" non deve per forza essere una "if" esplicita
// come nell'esempio precedente, ma può essere qualsiasi tipo di pattern matching
List(2, "due").collect {
  case x: Int => println(s"x è un Int! x * x = ${x * x}")
  case x: String => println(s"x è una String! x = $x")
}

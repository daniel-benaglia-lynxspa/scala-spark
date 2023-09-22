// Option è un oggetto utilizzato per rappresentare
// valori che potrebbero essere assenti

val valorePresente = true
def maybeValore: Option[Int] = if (valorePresente) {
  // Il costruttore di Option (definito nel companion object con "apply")
  // controlla se il valore è != null
  // se è lo viene ritornato un Some
  Option(10)
} else {
  // se non lo è viene ritornato un None
  Option(null.asInstanceOf[Int])
}

println(s"maybeValore=$maybeValore")

// I metodi che accettano funzioni dichiarati sulle Liste/Collezioni
// sono ripetuti perlopiù anche su Option
// e funzionano in modo analogo.
// E' molto utile considerare Option come una lista di 1 o 0 elementi
// Per ogni funzione ci sarà una descrizione di come funziona sulla lista e sull'Option
// l'intenzione è quella di sottolineare che sono concettualmente identici (per chi fosse interessato: Monads)

// Prima di vedere i metodi simili alle Liste, vale la pena evidenziare il metodo getOrElse
// getOrElse
// - Some: ritorna il valore contenuto nell'Option
// - None: ritorna il valore di default fornito
// N.B.: il tipo ritornato non è più un Option ma il valore "secco" contenuto,
// da Option[Int] passiamo ad Int
val valore: Int = maybeValore.getOrElse(0)



// map:
// map sulle liste applica la funzione fornita ai suoi elementi
// map sull'Option applica la funzione fornita ai suoi elementi: 1 elemento se Some, 0 se None

// - Some: ritorna un Some contenente il risultato della funzione applicata al valore interno al Some
// - None: ritorna None
maybeValore.map(_ * 2)


// filter:
// filter sulle liste tiene gli elementi che rispettano la condizione,
// filter sull'Option tiene gli elementi che rispettano la condizione (se l'elemento contenuto in un Some non la rispetta, l'Option diventa un None)
// - Some: ritorna un Some contenente il valore interno nel caso in cui la il predicato fornito è rispettato,
//         altrimenti None
// - None: ritorna None
maybeValore.filter(_ > 5)

// fold
// fold sulle liste applica la funzione ai suoi elementi, riducendoli ad un elemento solo, partendo da un valore iniziale, se la lista è vuota l'elemento ritornato sarà quello iniziale
// fold sull'Option applica la funzione ai suoi elementi, riducendoli ad un elemento solo, partendo da un valore iniziale, se l'Option è vuoto l'elemento ritornato sarà quello iniziale
// - Some: ritorna un Some contenente il risultato della funzione applicata al valore interno al Some
// - None: ritorna il valore di default fornito
maybeValore.fold(0)(_ * 2)
maybeValore.map(_ * 2).getOrElse(0) // <- questo equivale al fold della riga sopra

// flatMap
// flatMap sulle liste applica la funzione ai suoi elementi, la funzione deve produrre a sua volta altre liste, il risultato finale è una List singola,  non List di List
// flatMap sull'Option applica la funzione ai suoi elementi, la funzione deve produrre a sua volta un Option,   il risultato finale è un'Option singola, non Option di Option

// se una delle liste  della "catena" è vuota,   il risultato finale sarà una lista vuota
// se uno degli Option della "catena" è un None, il risultato finale sarà un None
def unAltroValoreOpzionale: Option[String] = None
maybeValore.flatMap { n =>
  unAltroValoreOpzionale.map(str => s"$str: $n")
}

// per "catena" si intende
List(1,2).flatMap(_ => List(1,2).flatMap(_ => List(1,2)))
Option(1).flatMap(_ => Option(1).flatMap(_ => Option(1)))

List.empty[Int].flatMap(_ =>List(1,2).flatMap(_ => List(1,2))) // Questa riga produrrà una lista vuota
Option.empty[Int].flatMap(_ => Option(1).flatMap(_ => Option(1))) // Questa riga produrrà un Option vuoto (None)

List(1,2).flatMap(_ => List.empty[Int].flatMap(_ => List(1,2))) // Questa riga produrrà una lista vuota
Option(1).flatMap(_ => Option.empty[Int].flatMap(_ => Option(1))) // Questa riga produrrà un Option vuoto (None)

List(1,2).flatMap(_ => List(1,2).flatMap(_ => List.empty[Int])) // Questa riga produrrà una lista vuota
Option(1).flatMap(_ => Option(1).flatMap(_ => Option.empty[Int])) // Questa riga produrrà un Option vuoto (None)

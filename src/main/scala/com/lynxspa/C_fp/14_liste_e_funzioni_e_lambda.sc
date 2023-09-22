// Le funzioni sono oggetti!
// Questo ci permette di avere dei metodi sulle liste
// che prendono funzioni come argomento
val lista = List(1, 2, 3, 4, 5)
val quadrato = (n: Int) => n * n


lista.map(x => quadrato(x))

lista.map(quadrato(_))

lista.map(quadrato)


// La funzione non deve per forza essere un metodo o una funzione
// esplicitamente dichiarata altrove, ma può essere una funzione anonima
// descritta direttamente durante la chiamata (Lambda!)


// lista.map
// map applica la funzione fornita ad ogni elemento della lista
// ritornando una nuova lista con gli elementi risultanti
// N.B.: La lista originale NON viene modificata
val risultato = lista.map(x => x * x)
// risultato contiene la nuova lista con i valori al quadrato
// lista contiene i valori originali


// filter ritorna una nuova lista contenente solo i valori che rispettano la funzione passata in input
// la funzione deve ritornare un Boolean
// se si utilizzano le parentesi graffe è permesso andare a capo per definire la logica della funzione
lista.filter { x =>
  x > 2
}

// reduce accetta una funzione che prende una coppia di elementi e che ne ritorna uno solo
// la firma di questo tipo di funzione è (Int, Int) => Int
// il risultato finale è un singolo elemento, dopo che tutti gli elementi della lista sono stati ridotti ad uno solo
// applicando la funzione a 2 elementi alla volta
lista.reduce((a: Int, b: Int) => a + b)
lista.reduce((a, b) => a + b)
lista.reduce(_ + _)
lista.sum


// fold funziona esattamente come reduce, ma permette di fornire un elemento iniziale
lista.fold(10)((a, b) => a + b)

// foldRight e foldLeft funzionano come fold, con l'importante differenza che l'elemento iniziale
// può essere di un tipo diverso di quello degli elementi della lista
// in questo caso la funzione passata in input deve essere
// (TipoElementoIniziale, TipoElementoLista) => TipoElementoIniziale
// il risultato finale sarà di TipoElementoIniziale, nell'esempio il tipo è String

// foldLeft parte dal primo elemento della lista, e applica la funzione usando il "risultatoAccumulato",
// che inizialmente è il valore iniziale fornito, nell'esempio "elemento iniziale"
// il risultato è quindi "elemento iniziale + 1"
// procede a fare lo stesso con i prossimi elementi, i risultati sono quindi
// elemento iniziale + 1 + 2
// elemento iniziale + 1 + 2 + 3
// elemento iniziale + 1 + 2 + 3 + 4
// elemento iniziale + 1 + 2 + 3 + 4 + 5 <- risultato finale del foldLeft
lista.foldLeft("elemento iniziale")((risultatoAccumulato, elementoLista) => {
  s"$risultatoAccumulato + $elementoLista"
})

// foldRight ha la differenza che gli elementi della lista vengono considerati partendo dall'ultimo
// N.B.: gli argomenti nella lambda sono invertiti!
// i risultati intermedi sono
// elemento iniziale + 5
// elemento iniziale + 5 + 4
// elemento iniziale + 5 + 4 + 3
// elemento iniziale + 5 + 4 + 3 + 2
// elemento iniziale + 5 + 4 + 3 + 2 + 1 <- risultato finale del foldRight
lista.foldRight("elemento iniziale")((elementoLista, risultatoAccumulato) => {
  s"$risultatoAccumulato + $elementoLista"
})

// flatten prende una Lista di Liste (o più generalmente una collezione di collezioni)
// e le concatena
// il risultato del seguente esempio è List(1, 2, 3, 4, 5, 6, 7, 8, 9)
List(
  List(1, 2, 3),
  List(4, 5, 6),
  List(7, 8, 9)
).flatten


// flatMap unisce due metodi in uno: map e flatten
// viene utilizzato quando si vuole ottenere una Lista di una sola dimensione (quindi non lista di liste)
// ma la funzione da applicare ad ogni elemento va a produrre a sua volta una lista per ogni elemento
lista.map { n =>
  Range(0, n) // <- questo ritorna una sequenza da 0 a N (escluso) per ogni elemento della lista
}
// il risultato dell'espressione è quindi (Range può essere visto come List, sono entrambi collezioni)
// List(Range(0), Range(0, 1), Range(0, 1, 2), Range(0, 1, 2, 3), Range(0, 1, 2, 3, 4))

lista.map { n =>
  Range(0, n)
}.flatten
// applicando  il flatten il risultato diventa una singola lista
// List(0, 0, 1, 0, 1, 2, 0, 1, 2, 3, 0, 1, 2, 3, 4)

// ha più senso in questo caso usare direttamente flatMap, che produce lo stesso risultato di map + flatten
lista.flatMap { n =>
  Range(0, n)
}
// List(0, 0, 1, 0, 1, 2, 0, 1, 2, 3, 0, 1, 2, 3, 4)

// Zip e Sliding

val listaUno = List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)


// Sliding produce un Iterator di liste, di dimensione N passato in input.
// Sliding accetta due argomenti, il primo è la dimensione della lista
// il secondo è la dimensione dello spostamento tra una lista e l'altra.
// Se si fornisce solo il primo, il secondo ha un valore di default = 1
listaUno.sliding(2).toList // toList per forzarne l'esecuzione e vedere il risultato
// List(List(0, 1), List(1, 2), List(2, 3) ...

listaUno.sliding(2, 2).toList
// List(List(0, 1), List(2, 3) ...


// Zip unisce due collezioni, producendo tuple 2 in cui il primo elemento è
// preso dalla prima lista, e il secondo dalla seconda
// N.B.: la dimensione della lista risultante è pari alla dimensione della lista più corta
val listaDue = List("A", "B", "C", "D", "E", "F", "G", "H", "I")
listaUno.zip(listaDue)
// List((0,A), (1,B), (2,C) ...

// La seguente tecnica è utilizzata spesso per paragonare
// gli elementi di una lista con l'elemento seguente
listaUno.zip(listaUno.drop(1)).foreach {
  case (a, b) => println(s"a: $a, b: $b")
}

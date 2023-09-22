// Cicli FOR e WHILE

val lista = List(1, 2, 3)


// Ciclo "for" equivalente a Java "for (Integer i: lista) { ... }"
for (n <- lista) {
  println(s"n * 2 = ${n * 2}")
}


// Cicli "while" e "do while", simili a Java
var i = 0
while (i < lista.size) {
  println(lista(i)) // Equivalente a Java "lista.get(i)"
  i+=1
}


var j = 0
do {
  println(lista(j)) // Equivalente a Java "lista.get(j)"
  j+=1
} while (j < lista.size)
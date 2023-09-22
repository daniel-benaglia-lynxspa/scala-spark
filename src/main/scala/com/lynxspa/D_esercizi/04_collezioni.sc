// Dichiarare una lista con i seguenti numeri:
// 1, -2, 3, -4, -5, 6, 7, 8, -9

val list = List(1, -2, 3, -4, -5, 6, 7, 8, -9)


// Produrre una lista con i numeri al quadrato
list.map(n => n * n)




// Calcolare la somma dei numeri al cubo
list.map(n => n * n * n).reduce(_ + _)



// Calcolare la somma dei numeri al cubo, ignorando i numeri negativi
list
  .filter(_ >= 0)
  .map(n => n * n * n)
  .reduce(_ + _)
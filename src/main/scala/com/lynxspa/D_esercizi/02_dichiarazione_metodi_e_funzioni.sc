// Dichiarare un metodo che somma due numeri in input

def somma(a : Int, b : Int) = {
  println(s"${a+b}")
}



// Dichiarare un metodo che accetta un numero e ritorna la stringa:
// "pari" se il numero è pari
// "dispari" se il numero è dispari
def metodo(a : Int): String = {
  if (a % 2 == 0) {
    "pari"
  } else {
    "dispari"
  }
}

// Dichiarare una funzione che somma due numeri in input
def somma(a : Int, b : Int) = a + b
val sommaF = somma _
sommaF(1, 2)



// Dichiarare un metodo che divide due numeri in input
// gestire la divisione per 0
def divisione(dividendo: Double, divisore: Double) = {
  if (divisore == 0) {
    None
  } else {
    Some(dividendo / divisore)
  }
}

divisione(10, 0)
/*
// *** EXTRA ***
// Dichiarare un metodo che divide due numeri in input
// gestire la divisione per 0 utilizzando una funzione parziale
*/


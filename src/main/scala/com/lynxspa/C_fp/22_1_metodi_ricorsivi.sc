import scala.annotation.tailrec
// Metodi ricorsivi

// Un metodo ricorsivo è un metodo che chiama se stesso
// è obbligatorio dichiarare il tipo ritornato da un tipo ricorsivo, 
// omettendo :Long il codice non compilerebbe

// L'annotazione @tailrec permette al compilatore di verificare che la chiamata ricorsiva
// può essere ottimizzata in modo da non ottenere StackOverflowError 
// nel caso in cui il numero di chiamate eccede la dimensione dello stack
@tailrec
def somma(n: Long, accumulatore: Long = 0): Long = {
  if (n <= 0) {
    accumulatore
  } else {
    // per essere dichiarato tailrec il metodo deve finire con la chiamata ricorsiva
    somma(n - 1, n + accumulatore)
  }
}

// N.B.: l'annotazione @tailrec non cambia nulla una volta che il codice è compilato,
// se la funzione ricorsiva era ottimizzabile, il compilatore la ottimizza.
// L'annotazione serve solo a fare fallire la compilazione nel momento in cui un metodo
// annotato come @tailrec non lo è, e quindi non è ottimizzabile.
// Tentando di annotare il seguente metodo, otteniamo l'errore di compilazione
//    could not optimize @tailrec annotated method sommaNonTailrec:
//    it contains a recursive call not in tail position
def sommaNonTailrec(n: Long): Long = {
  if (n <= 0) {
    0
  } else {
    // questo metodo non termina solo con la chiamata ricorsiva
    // e di conseguenza ogni chiamata deve essere inserita nella stack.
    // Questo metodo non è tailrec
    n + sommaNonTailrec(n - 1)
  }
}

val n = 40000

somma(n) // ok

sommaNonTailrec(n) // stack overflow!

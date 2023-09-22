import scala.annotation.tailrec
// Metodi annidati


// Nulla ci impedisce di dichiarare un metodo dentro ad un altro metodo.
// Questo viene spesso fatto durante la dichiarazione di metodi ricorsivi
// in modo da rendere la firma esterna più chiara,
// senza esporre eventuali accumulatori o valori utili solo internamente
def somma(n: Long): Long = {

  // dichiarazione metodo interno "menoUno"
  def menoUno(n: Long) = n - 1

  // dichiarazione metodo ricorsivo interno "inner" (convezione)
  @tailrec
  def inner(n: Long, accumulatore: Long): Long = {
    if (n <= 0) {
      accumulatore
    } else {
      // utilizzo metodo interno "menoUno"
      inner(menoUno(n), n + accumulatore)
    }
  }

  // inizio logica metodo esterno che semplicemente chiama il metodo ricorsivo interno
  // fornendo tutti i dati necessari (accumulatore) che non volevamo esporre ai chiamanti di "somma"
  inner(n, 0)
}

1 + 2 + 3 + 4 + 5
somma(5)

// Dichiarare un metodo che stampa la tabellina
// delle moltiplicazioni di un numero (fino a 10)
// n = 3

def tabellina(n: Int): Unit = {
  val unoDaDieci = Range.inclusive(1, 10)
  unoDaDieci.foreach { elemento =>
    println(s"$n x $elemento = ${n*elemento}")
  }
}

tabellina(3)




// Dichiarare un metodo che stampa la tabellina
// delle moltiplicazioni di un numero
// (fino a un altro numero passato in input)
def tabellina(n: Int, fine: Int): Unit = {
  val unoAFine = Range.inclusive(1, fine)
  unoAFine.foreach { elemento =>
    println(s"$n x $elemento = ${n*elemento}")
  }
}




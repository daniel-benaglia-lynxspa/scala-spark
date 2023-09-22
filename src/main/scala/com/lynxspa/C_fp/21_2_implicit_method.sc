
/*implicit rimosso*/class RichInt(self: Int) {
  def piu(n: Int): Int = self + n
  def meno(n: Int): Int = self - n
  def per(n: Int): Int = self * n
  def diviso(n: Int): Int = self / n
}

// Possiamo ottenere lo stesso identico risultato del capitolo precedente
// senza dichiarare la classe implicita, ma fornendo un metodo implicito
// che si occupa di creare la nuova classe

implicit def conversioneImplicita(n: Int): RichInt = new RichInt(n)

10 piu 4
10 meno 4
10 per 4
10 diviso 4

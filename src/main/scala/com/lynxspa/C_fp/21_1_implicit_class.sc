// Implicits: da un grande potere derivano grandi responsabilità

implicit class RichInt(self: Int) {
  def piu(n: Int): Int = self + n
  def meno(n: Int): Int = self - n
  def per(n: Int): Int = self * n
  def diviso(n: Int): Int = self / n
}

// Una classe definita implicita che prende un solo argomento
// viene utilizzata per aggiungere metodi al tipo passato in input
// in questo caso abbiamo definito i metodi "piu" "meno" "per" "diviso"
// sulla classe Int

// Possiamo quindi chiamare i nuovi metodi
10.piu(4)


// Scala ci permette di non dover scrivere il punto e le parentesi chiamando metodi con 1 solo argomento
// In questo caso ha senso utilizzare la sintassi semplificata
10 piu 4
10 meno 4
10 per 4
10 diviso 4

// N.B.: anche quando chiamiamo un semplice "+" stiamo usando la sintassi "semplificata"
// di un vero metodo, possiamo usarlo con la sintassi classica
10.+(2)
10.-(2)
10.*(2)
10./(2)
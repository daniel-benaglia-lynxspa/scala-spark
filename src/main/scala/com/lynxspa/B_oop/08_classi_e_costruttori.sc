// Classi e costruttori

// class                    -> parola chiave come in Java,
//                             public non esiste come parola chiave, è già così di default se non dichiarato private
// Quadrato                 -> nome classe
// (private val lato: Int)  -> costruttore dichiarato come parte della definizione della classe,
//                             simile ad un metodo o ad una funzione
//
class Quadrato(private var lato: Int,
               private var colore: String) {

  // Si può scrivere codice all'interno della classe, e al di fuori dei metodi,
  // contrariamente a come si può fare in Java. Questo codice viene eseguito subito dopo la costruzione della classe
  println("Stampato subito quando la classe viene creata, simile a come accadrebbe in un costruttore Java")

  def getArea: Int = lato * lato

  // Getter e Setter come verrebbe normalmente fatto in Java.
  // Difficilmente viene seguito questo pattern in Scala, dove si preferiscono i valori immutabili e pubblici
  def setLato(l: Int) = {
    this.lato = l
  }

  def getLato(): Int = {
    this.lato
  }

}

// Chiamata al costruttore, come Java
val q = new Quadrato(3, "rosso")

// Chiamata ai metodi, notare come getArea è chiamato senza parentesi, visto che è dichiarato senza
println(q.getArea)
println(q.getLato())
q.setLato(42)
println(q.getLato())
println(q.getArea)

// q.lato <- errore in quanto è privato, come accadrebbe in Java

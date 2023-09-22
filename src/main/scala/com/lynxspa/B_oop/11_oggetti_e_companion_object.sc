// Oggetti AKA Singleton + Companion Object


// In Scala esiste il concetto di "object" che replica il pattern del "Singleton" in Java
// Quando si dichiara un object, questo viene istanziato immediatamente, ed è l'unica istanza che può esistere.
// Tutti i campi / metodi di un object sono statici.

// Una use-case tipica è quella di utilizzarlo come namespace per dei valori costanti
object Costanti {
  val zero = 0
  val uno = 1
  val piGreco = 3.141592653589793
}

// Un'altra use-case è quella di contenitore di funzioni.
// Questo evita spesso il problema del "TaskNotSerializable" in Spark. Tema che verrà discusso più avanti.
object FunzioniSpark {
  def f(n: Int): Int = n * n
}

// Utilizzo dell'object esternamente, tutto è statico
FunzioniSpark.f(1)


//new Costanti <- errore di compilazione, "Costanti" è già istanziato, non si può re-istanziare






// Il "companion object" è un oggetto dichiarato nello stesso file di una classe
// utilizzando lo stesso nome della classe
class Quadrato(val lato: Int, val colore: String) {
  def getArea: Int = lato * lato
}

// Questo è il modo in cui le classi in scala possono avere metodi statici, la parola chiave static non esiste
object Quadrato {
  def metodoStatico(lato: Int): Unit = {
    // N.B.: qui stiamo chiamando il costruttore "apply" definito sotto,
    // visto che non stiamo usando la parola chiave "new"
    val q = Quadrato(lato)
    println(s"L'area di un quadrato con lato $lato = ${q.getArea}")
  }

  // Questo è inoltre il modo per fornire nuovi costruttori che vengono chiamati senza la parola chiave "new"
  // Questi costruttori devono essere chiamati "apply"
  def apply(lato: Int) = new Quadrato(lato, "rosa")
}

// Il metodo statico viene chiamato direttamente sull'oggetto da un contesto statico
Quadrato.metodoStatico(3)

// N.B.: più genericamente quando viene definito un metodo chiamato "apply", questo sarà il metodo chiamato
// quando l'oggetto su cui è definito viene chiamato come un metodo/funzione ovvero con le parentesi tonde
// Esempio:
object UnOggettoConMetodoApply {
  def apply(): Unit = {
    println("chiamato il metodo apply di UnOggettoConMetodoApply")
  }
}

// Le prossime due righe sono equivalenti
UnOggettoConMetodoApply.apply()
UnOggettoConMetodoApply()

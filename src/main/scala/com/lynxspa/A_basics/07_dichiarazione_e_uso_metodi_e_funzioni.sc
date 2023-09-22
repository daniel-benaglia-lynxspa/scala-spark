// Dichiarazione e uso metodi e funzioni


// def        -> parola chiave per dichiarare un metodo
// metodo     -> il nome del metodo
// (n: Int)   -> argomenti, il tipo va esplicitato
// : Int      -> tipo ritornato, opzionale ma consigliato
// = { ... }  -> corpo del metodo, l'ultima riga è il valore ritornato
def metodo(n: Int): Int = {
  n * n
}
// senza tipo di ritorno
def metodo2(n: Int) = {
  n * n
}
// su una singola riga
def metodo3(n: Int) = n * n

// Gli argomenti di un metodo possono essere passati esplicitandone il nome
metodo(n = 1)

// val        -> la funzione è assegnata come una variabile, vale lo stesso discorso di val/var
// funzione   -> il nome della funzione (una funzione senza nome è una "Lambda")
// =          -> la funzione è assegnata come una variabile, serve quindi l'uguale per iniziare a definirla
// (n: Int)   -> argomenti input della funzione
// => { ... } -> la "freccia" separa gli argomenti dal corpo della funzione, come l'uguale per i metodi
val funzione = (n: Int) => {
  n * n
}

// singola riga
val funzione2 = (n: Int) => n * n



// Il tipo di ritorno "void" di Java si dichiara "Unit" in scala
def metodoVoid(): Unit = {
  println("metodo void")
}

// N.B.: Attenzione che se un metodo è dichiarato esplicitamente Unit,
// questo non ritornerà mai nulla di diverso di Unit, anche se la logica del metodo lo permette
def metodoDichiaratoUnit(): Unit = {
  // questo metodo ritornerebbe il valore 10 se non fosse dichiarato come Unit
  10
}
val risultatoMetodoDichiaratoUnit = metodoDichiaratoUnit()
println(risultatoMetodoDichiaratoUnit) // <- stampa le parentesi vuote () che corrispondono a Unit




// I metodi (ma non le funzioni) possono avere valori di default
def metodoConValoriDefault(valoreNonDefault: Int, valoreDefault: String = "default"): String = {
  s"valoreNonDefault=$valoreNonDefault, valoreDefault=$valoreDefault"
}

// Il modo canonico di fornire dei valori di default ad una funzione è quello di applicarli parzialmente
val funzioneConDueArgomenti = (argUno: Int, argDue: String) => s"argUno=$argUno, argDue=$argDue"

val funzioneConDefault = funzioneConDueArgomenti(_: Int, "default")

// Lo stesso si può ottenere nel seguente modo
val funzioneConDefault2 = (argUno: Int) => funzioneConDueArgomenti(argUno, "default")

metodoConValoriDefault(2)   // valoreNonDefault=2, valoreDefault=default
funzioneConDefault(2)                     // argUno=2, argDue=default
funzioneConDefault2(2)                    // argUno=2, argDue=default



// anche dichiarando una funzione senza argomenti è comunque necessario dichiarare quali sono gli argomenti,
// in questo caso essendo gli argomenti assenti, si dichiara usando le parentesi vuote.
// Questo ha più senso se pensa a come la funzione verrà chiamata,
// ovvero con le parentesi vuote: funzioneSenzaArgomenti()
val funzioneSenzaArgomenti = () => println("funzione chiamata")

// Le funzioni e i metodi vengono chiamate nello stesso modo, come in Java
// nomeMetodoOFunzione(argomento1, argomento2)
metodo(2)
metodo2(2)
metodo3(2)
funzione(2)


// N.B.:
// Un metodo senza argomenti può venire chiamato senza parentesi,
// se nella definizione del metodo non sono state usate parentesi
def metodoConParentesi() = println("chiamato metodo con parentesi")

def metodoSenzaParentesi = println("chiamato metodo senza parentesi")

metodoConParentesi()
metodoSenzaParentesi

// metodoSenzaParentesi() <- errore di compilazione

// E' possibile chiamare senza usare le parentesi un metodo che le dichiara,
// anche se sconsigliato in quanto potrebbe creare confusione
metodoConParentesi


// I metodi e le funzioni possono avere più gruppi di argomenti
def metodoConPiuGruppiDiArgomenti(grpUnoInt: Int, grpUnoStr: String)
                                 (grpDueInt: Int, grpDueStr: String): String = {
  val unoPerDue = grpUnoInt * grpDueInt
  s"$grpUnoStr, $grpDueStr, $unoPerDue"
}

// Attenzione che la funzione con più gruppi di argomenti è essenzialmente una funzione
// che prende il primo gruppo e ritorna una funzione che vuole il secondo gruppo per ritornare il risultato finale.
// Il tipo della seguente funzione è (Int, String) => (Int, String) => String
val funzioneConPiuGruppiDiArgomenti =
(grpUnoInt: Int, grpUnoStr: String) =>
  (grpDueInt: Int, grpDueStr: String) => {
      val unoPerDue = grpUnoInt * grpDueInt
      s"$grpUnoStr, $grpDueStr, $unoPerDue"
    }

// Il metodo può essere chiamato normalmente, fornendo tutti gli argomenti
metodoConPiuGruppiDiArgomenti(1, "uno")(2, "due")
// Come la funzione
funzioneConPiuGruppiDiArgomenti(1, "uno")(2, "due")

// o può essere chiamato fornendo solo un gruppo, seguito da un underscore,
// simile a chiamare un metodo/funzione senza fornire tutti gli arogmenti
val parzialmenteApplicato = metodoConPiuGruppiDiArgomenti(1, "uno") _
// Applicare parzialmente la funzione è più canonico e meno verboso,
// come detto precedentemente così facendo stiamo ottenendo una funzione che vuole il secondo gruppo di argomenti
val parzialmenteApplicatoF = funzioneConPiuGruppiDiArgomenti(1, "uno")

// questa chiamata finale equivale alla chiamata con tutti gli argomenti qualche riga sopra
// in quanto gli argomenti del gruppo uno sono già stati forniti, e sono uguali nei due esempi
parzialmenteApplicato(2, "due")
parzialmenteApplicatoF(2, "due")

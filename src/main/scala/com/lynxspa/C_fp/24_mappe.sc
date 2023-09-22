// Mappe

// Le mappe funzionano come in Java, cambia solamente la sintassi
// Map<String>

// Costruttore di Map chiamato con la tupla 2
val m = Map(
  (1, "uno"),
  (2, "due"),
  (3, "tre")
)

// Se la nostra tupla 2 rappresenta una coppia di chiave-valore
// è consigliabile utilizzare la sintassi speciale con la freccia
val q = Map(
  1 -> "uno",
  2 -> "due",
  3 -> "tre"
)

// Possiamo ottenere un valore passando una chiave,
// chiamando la mappa come se fosse una funzione/metodo
// N.B.: quando si chiama una classe come se fosse una funzione/metodo
// stiamo andando a chiamare il suo metodo "apply"
// Le 2 righe seguenti sono equivalenti
val tre = q(3)
val sempreTre = q.apply(3)


// Nell'esempio precedente, se non fosse presente la chiave 3, otterremmo un errore.
// Un modo più sicuro di accedere ai valori di una mappa è il metodo "get" che ritorna un Option.
// L'Option sarà vuota nel caso non ci fosse la chiave
val someTre = q.get(3)
val none = q.get(4)

// Ricordo la presenza sull'Option dei metodi simili a quelli delle liste.
// Non è necessario verificare o meno la presenza di un valore all'interno di un Option
// come non è necessario (spesso) verificare se una lista è vuota o meno.
// Basta "iterare" sui suoi valori ed applicare la logica desiderata
val forseTreUppercase = q.get(3).map(_.toUpperCase)
val forseQuattroUpperCase = q.get(4).map(_.toUpperCase)

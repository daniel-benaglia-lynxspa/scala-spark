// Tuple

// Le tuple sono sequenze finite di elementi eterogenei

// Diverse dichiarazioni di una tupla 3
// La sintassi utilizzata solitamente è l'ultima (t3)
val t1 = new Tuple3(1, "uno", List(1))
val t2 = Tuple3(1, "uno", List(1))
val t3 = (1, "uno", List(1))

// La tupla 2 è speciale, viene spesso utilizzata per rappresentare coppie di chiave-valore
// per questo può essere costruita con una sintassi speciale
// N.B.: questo è solo zucchero sintattico, in pratica non cambia nulla
"chiave" -> "valore"
("chiave", "valore")
Tuple2("chiave", "valore")

// Pattern matching sulle tuple, come sulle case class
t3 match {
  case Tuple3(id, nomeCliente, lista) =>
    println(s"nome cliente: $nomeCliente")
}
// Come durante la dichiarazione, si può omettere il nome
// Possiamo inoltre evitare di dare un nome ai valori che non ci interessano
// Otteniamo così una sintassi più leggera
t3 match {
  case (_, nomeCliente, _) =>
    println(s"nome cliente: $nomeCliente")
}

// Si può accedere ai vari campi di una tupla utilizzando _N dove N è la posizione del campo, partendo da 1
val primoCampo: Int       = t3._1
val secondoCampo: String  = t3._2
val terzoCampo: List[Int] = t3._3

// Le tuple si possono annidare, i valori di una tupla possono essere a loro volta tuple
val list = List(
  (1, (1, 2), List(1)),
  (1, (1, 2), List(1)),
  (1, (1, 2), List(1)),
  (1, (1, 2), List(1)),
  (1, (1, 2), List(1))
)

// In casi di tuple annidate è preferibile evitare la sintassi con gli underscore
// ed utilizzare invece il pattern matching per esplicitare il significato dei campi
val secondoElementoDelSecondoElemento: List[Int] = list.map(_._2._2)

val comeSopraMaConPatternMatching: List[Int] = list.map {
  case (primo, (secondoPrimo, secondoSecondo), terzo) =>
    secondoSecondo
}

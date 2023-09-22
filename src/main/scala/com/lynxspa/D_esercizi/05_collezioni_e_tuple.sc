// Dichiarare una lista di tuple con i seguenti dati
// (1 riga = 1 tupla)
// Italia, Roma
// Germania, Berlino
// Francia, Parigi
// Spagna, Madrid
// Norvegia, Oslo
val list = List(
  ("Italia", "Roma"),
  ("Germania", "Berlino"),
  ("Francia", "Parigi"),
  ("Spagna", "Madrid"),
  ("Norvegia", "Oslo")
)
// Dichiarare una mappa con i dati sopracitati
val mappa = Map(
  ("Italia", "Roma"),
  ("Germania", "Berlino"),
  ("Francia", "Parigi"),
  ("Spagna", "Madrid"),
  ("Norvegia", "Oslo")
)
// Dichiarare un metodo che dato il nome di un paese ne stampa la capitale
def metodo(nome: String): Unit = {
  mappa.get(nome).foreach(println)
}

metodo("X")

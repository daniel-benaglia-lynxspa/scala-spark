// Dichiarare una lista di classi con i seguenti dati
// (1 riga = 1 classe)

// nome, capitale, popolazione
// Italia, Roma, 59110000
// Germania, Berlino, 84358845
// Francia, Parigi, 67750000
// Spagna, Madrid, 47420000
// Norvegia, Oslo, 5408000
case class Paese(nome: String, capitale: String, popolazione: Int)
val lista = List(
  Paese("Italia","Roma",5911000),
  Paese("Germania", "Berlino", 84358845),
  Paese("Francia", "Parigi", 67750000),
  Paese("Spagna", "Madrid", 47420000),
  Paese("Norvegia", "Oslo", 5408000)
)

// Trovare la popolazione media (aritmetica) dei paesi
lista.map(_.popolazione).sum / lista.length



// Trovare i paesi con popolazione superiore a N
val n = 6000000
lista.filter(_.popolazione > n)




// Creare una lista con le capitali dei paesi con popolazione superiore a N
lista.filter(_.popolazione > n).map(_.capitale)

List(1,2,3).max
case class Paese(nome: String, capitale: String, popolazione: Int)
val lista = List(
  Paese("Italia","Roma",5911000),
  Paese("Germania", "Berlino", 84358845),
  Paese("Francia", "Parigi", 67750000),
  Paese("Spagna", "Madrid", 47420000),
  Paese("Norvegia", "Oslo", 5408000)
)

// Trovare il nome del paese con la popolazione massima
lista.reduce((a, b) => if (a.popolazione > b.popolazione) a else b)

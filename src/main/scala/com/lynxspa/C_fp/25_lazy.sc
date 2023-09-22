// Lazy

println("inizio file")

// La parola chiave lazy indica un valore che verrà calcolato
// solo nel momento in cui viene utilizzato
// Questo è molto utile per valori complessi da calcolare che potrebbero non servire
lazy val booleanoLazy: Boolean = {
  println("calcolo boolean lazy...")
  true
}

val booleanoNormaleFalse: Boolean = {
  println("calcolo boolean normale...")
  false
}

val booleanoNormaleDue: Boolean = {
  println("calcolo boolean normale due...")
  true
}

if (booleanoNormaleFalse && booleanoLazy) {
  println("true")
} else {
  println("false")
}

if (booleanoNormaleFalse && booleanoNormaleDue) {
  println("true")
} else {
  println("false")
}

// Eseguendo il codice in questo file, non leggeremo mai "calcolo boolean lazy..." nell'output
// perchè non è mai stato necessario calcolarlo, la condizione si è fermata al primo termine

// Vediamo invece che viene scritto "calcolo boolean normale due..." anche se, come per quello lazy,
// non è poi stato utilizzato nella condizione
// Tutto ha un valore, il return "non esiste"

// Assegnazione semplice
val uno = 1

// Assegnazione semplice con tipo esplicito
val s: String = "stringa"

// Assegnazione del risultato di un'espressione
val unoPiuUno = {
  1 + 1
}
println(unoPiuUno)

// Assegnazione del risultato di un'espressione a più righe
// L'ultima riga è il risultato, il return non esiste
// In questo caso tutte le righe precedenti all'ultima non hanno alcun impatto
// Fatta eccezione per il println che stamperà "abc" senza però influenzare il valore della variabile
val espressioneAPiuRighe = {
  1 + 1
  println("abc")
  List(1, 2, 3, 4)
  "a"
}
println(espressioneAPiuRighe)


// N.B.: Se in una situazione come questa ci si dimenticano gli operatori binari tra una riga e l'altra
// Quindi ad esempio && e ||, avrà effetto solo l'ultima riga
val condizioneAPiuRighe = {
  1 == 0 &&
  0 == 2 &&
  0 == 0
}
println(condizioneAPiuRighe)


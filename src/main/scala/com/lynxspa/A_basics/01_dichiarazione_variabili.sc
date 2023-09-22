// Dichiarazione variabili

// Una variabile dichiarata con "val" è immutabile (final in Java), non può essere riassegnata
val x: Int = 1
// x = 2 risulterà in un errore

// Una variabile dichiarata con "var" è modificabile
var y: Int = 1
y = 2
y = 3

// Il tipo si può omettere
val z = 1

println(z.getClass) // -> int

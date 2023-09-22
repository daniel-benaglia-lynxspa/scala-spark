// Le funzioni sono oggetti !!!



val funzione: Int => Int = (n: Int) => n * n
// funzione è un "oggetto" di tipo Int => Int
println(funzione) // <- stampa "<function1>"

// come visto prima, dichiarare il tipo è opzionale
val funzioneSenzaTipoEsplicito = (n: Int) => n * n


// visto che le funzioni sono oggetti
// possono essere passate come argomenti ad altre funzioni/metodi
// in questo semplice esempio, la "funzione" in input viene applicata all'"argomento"
val applicaFunzione = (funzione: Int => Int, argomento: Int) => {
  funzione(argomento)
}

// lo stesso vale per i metodi, metodi e funzioni si comportano nello stesso modo
def metodoApplicaFunzione(funzione: Int => Int, argomento: Int) = {
  funzione(argomento)
}

// si possono chiamare i metodi e le funzioni solo con una parte degli argomenti
// questo applica parzialmente la funzione e ritorna una nuova funzione che richiede
// solo gli argomenti non già forniti

// gli argomenti non passati devono essere dichiarati con il simbolo "wildcard", in Scala è un underscore
// va anche esplicitato nuovamente il tipo richiesto, quindi la sintassi diventa
// _: Int
val applicaQuadrato = applicaFunzione(funzione, _: Int)

// in questo caso la funzione "applicaQuadrato" richiede solo un Int,
// in quanto la funzione Int => Int è già stata fornita
applicaQuadrato(4)


// Le funzioni possono essere dichiarate "inline" quando servono, senza assegnarle a variabili
// e quindi senza dare un nome, queste funzioni si definiscono "Lambda"
// in questo esempio viene dichiarata la funzione direttamente nella chiamata al metodo
val applicaCubo = metodoApplicaFunzione(x => x * x * x, _: Int)
applicaCubo(3)

// Implicits: da un grande potere derivano grandi responsabilità


// Ci sono situazioni in cui un determinato oggetto, come ad esempio una connessione,
// devono essere continuamente passate a metodi che lo utilizzano
case class Context(connessione: String)


// Ad esempio a metodi che accedono al database e hanno bisogno dei dettagli della connessione
def trovaCliente(idCliente: Int, context: Context): Unit = { println(s"trovaCliente $idCliente $context") }
def aggiornaCliente(idCliente: Int, context: Context): Unit = { println(s"aggiornaCliente $idCliente $context") }
def salvaCliente(idCliente: Int, context: Context): Unit = { println(s"salvaCliente $idCliente $context") }

val ctx = Context("conn")
val id = 123

// Questo ci obbliga a doverlo passare esplicitamente ogni volta, appesantendo il codice,
// e rendendolo più difficile da leggere
trovaCliente(id, ctx)
aggiornaCliente(id, ctx)
salvaCliente(id, ctx)

// Gli impliciti ci permettono di passare alcuni elementi senza doverlo fare esplicitamente
implicit val ctxImpl: Context = Context("conn impl")

def trovaClienteImpl(idCliente: Int)
                    (implicit context: Context): Unit = {
  println(s"trovaClienteImpl $idCliente $context")
}

trovaClienteImpl(id)
case class CommitToDatabase(value: Boolean)

def salvaClienteImpl(idCliente: Int)
                    (implicit context: Context, commit: CommitToDatabase): Unit = {
  println(s"salvaClienteImpl $idCliente $context $commit")
}

implicit val c: CommitToDatabase = CommitToDatabase(false)

salvaClienteImpl(id)


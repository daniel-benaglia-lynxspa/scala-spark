// Pattern Matching

// Il pattern matching usa il metodo "unapply" del companion object per estrarre i vari campi forniti dal metodo
// Esempio esplicito con una classe normale e metodo unapply definito manualmente
class UnaClasse(val campoInt: Int, val campoStr: String)
object UnaClasse {
  def unapply(unaClasse: UnaClasse) = Some(unaClasse.campoInt)
}

val uc = new UnaClasse(1, "uno")
uc match {
  // Nel pattern matching ci viene fornito solo il "campoInt" in quanto è quello fornito dal metodo unapply
  // per le case class il metodo unapply implementato automaticamente include tutti i campi
  case UnaClasse(campoInt) => println(s"Il campoInt = $campoInt")
}


uc match {
  // Possiamo comunque accedere al "campoStr" utilizzando un nome da assegnare a tutto l'elemento
  // con la sintassi nome@Classe
  case elemento@UnaClasse(ilCampoInt) =>
    println(s"Il campoInt = $ilCampoInt")
    println(s"Il campoStr = ${elemento.campoStr}")
}


// Esempio con le case class in cui viene implementato automaticamente il metodo unapply

// la parola chiave "sealed" obbliga tutte le implementazioni ad essere definite nello stesso file,
// questo permette al compilatore di sapere che il tipo somma Figura è composto solo da Quadrato e Rettangolo,
// avvisandoci nel caso il pattern matching su questo tipo è incompleto
sealed trait Figura {
  def area: Double
}
case class Quadrato(lato: Double) extends Figura {
  override def area: Double = lato * lato
}
case class Rettangolo(lunghezza: Double, larghezza: Double) extends Figura {
  override def area: Double = lunghezza * larghezza
}

val listaDiFigure: List[Figura] = List(
  Quadrato(3),
  Rettangolo(2, 5),
  Quadrato(4),
  Rettangolo(1, 3)
)

// Pattern matching completo, visto che prevede sia il caso Quadrato che Rettangolo
listaDiFigure.foreach {
  // viene chiamato Quadrato.unapply per ottenere il lato
  case Quadrato(lato) => println(s"il lato del quadrato = $lato")
  // viene chiamato Rettangolo.unapply per ottenere i lati
  case Rettangolo(a, b) => println(s"i lati del ret sono $a e $b")
}


// Esempio di match incompleto
sealed trait UnTrait
case class A() extends UnTrait
case class B() extends UnTrait
case class C() extends UnTrait

val listaDiUnTrait: List[UnTrait] = List(
  A(),
  B(),
  C()
)

// Warning durante la compilazione: match may not be exhaustive (manca la classe C)
// E errore durante l'esecuzione: scala.MatchError: C() (of class C)
listaDiUnTrait.foreach {
  case A() => println("A")
  case B() => println("B")
  case _ => println("default")
}


// Collect + Pattern Matching
sealed trait UnTrait
case class CaseClassInt(n: Int) extends UnTrait
case class CaseClassStr(s: String) extends UnTrait

val listaDiUnTrait: List[UnTrait] = List(
  CaseClassInt(5),
  CaseClassStr("cinque")
)
// Possiamo utilizzare collect per ignorare alcune classi
// e operare solo su quelle dove il pattern matching è definito
val quadratiDeiNumeriDelleCaseClassInt = listaDiUnTrait.collect {
  case CaseClassInt(n) => n * n
}

// Collect esiste anche su option, funziona allo stesso modo
// si può spesso pensare ad Option come una lista di 1 o 0 elementi
Option(10).collect {
  case x if x > 10 => x * x
}
Option(CaseClassInt(3)).collect {
  case CaseClassInt(n) => println(s"Nell'Option c'è una CaseClassInt, il suo valore è $n")
}
val forseOtto: Option[Int] = Option(CaseClassInt(4)).collect {
  case CaseClassInt(n) => n * 2
}
Option(CaseClassInt(3)).collect {
  case CaseClassStr(str) => println("questo non verrà mai stampato, in quanto l'option non contiene CaseClassStr")
}
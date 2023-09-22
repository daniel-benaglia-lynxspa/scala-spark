// Espressioni condizionali: MATCH

val tre = 3

tre match {
  case 1 => "il valore = 1"
  case 3 => "il valore = 3"
  case _ => "default"
}

/*
// Equivale ad un Java "switch case"

switch(tre) {
  case 1:
    return "il valore è 1";
  case 2:
    return "il valore è 2";
  case 3:
    return "il valore è 2";
 default:
    return "comportamento default";
}
*/
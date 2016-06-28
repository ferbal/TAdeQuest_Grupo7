package tadp

trait Posicion 

case object Cabeza extends Posicion
case object ManoIzq extends Posicion
case object ManoDer extends Posicion
case object UnaMano extends Posicion
case object AmbasManos extends Posicion
case object Torso extends Posicion
case object Talismanes extends Posicion

trait Habilidad

case object Mago extends Habilidad
case object Ladron extends Habilidad
case object Guerrero extends Habilidad

trait StatPrincipal

case object Fuerza extends StatPrincipal
case object Hp extends StatPrincipal
case object Inteligencia extends StatPrincipal
case object Velocidad extends StatPrincipal

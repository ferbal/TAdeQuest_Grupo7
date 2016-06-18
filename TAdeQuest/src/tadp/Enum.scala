package tadp

object Posicion extends Enumeration {
  val CABEZA = "CABEZA"
  val MANO_IZQ = "MANO_IZQUIERDA"
  val MANO_DER = "MANO_DERECHA"
  val AMBAS_MANOS = "AMBAS MANOS"
  val TORSO = "TORSO"
  val TALISMANES = "TALISMANES"
}

object Habilidad extends Enumeration {
  val GUERRERO = "GUERRERO"
  val MAGO = "MAGO"
  val LADRON = "LADRON"
  val SIN_TRABAJO = "SIN TRABAJO"
}

object Stat_Principal extends Enumeration {
  val NINGUNO = "NINGUNO"
  val FUERZA = "FUERZA"
  val HP = "HP"
  val VELOCIDAD = "VELOCIDAD"
  val INTELIGENCIA = "INTELIGENCIA"
  
}

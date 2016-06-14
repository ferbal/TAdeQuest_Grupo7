package test

import org.junit.Test
import org.junit.Assert._
import tadp.Heroe
import tadp.Posicion
import tadp.Casco_Supremo
import tadp.Casco
import tadp.Espada_Doble
import tadp.Espada_Zurda
import tadp.Espada_Zurda
import tadp.Espada_Doble
import tadp.Palito_Magico
import tadp.Habilidad
import tadp.Vincha_Bufalo_Agua

class Tests{

  @Test
  def pruebaInicial():Unit = {
    
    val guerrero = new Heroe
    val palito = new Palito_Magico
    val ladron = new Heroe
    val mago = new Heroe
    val vincha = new Vincha_Bufalo_Agua
    
    guerrero.utilizar_item(Posicion.CABEZA, new Casco_Supremo)
    guerrero.utilizar_item(Posicion.AMBAS_MANOS, new Espada_Doble)
    guerrero.utilizar_item(Posicion.MANO_IZQ, new Espada_Zurda)
    guerrero.utilizar_item(Posicion.AMBAS_MANOS, new Espada_Doble)
    //println("HP Base: " + guerrero.stats.hp)
    //println("HP Trabajo: " + guerrero.trabajo.stats.hp)
    println("HP Actual: " + guerrero.get_hp_actual)       
    guerrero.definir_trabajo(Habilidad.GUERRERO)
    println("HP Actual: " + guerrero.get_hp_actual)
    
    ladron.definir_trabajo(Habilidad.LADRON)
    
    assertEquals(false,vincha.puede_usar(ladron))
    
    ladron.definir_trabajo(Habilidad.SIN_TRABAJO)
    
    assertEquals(true,vincha.puede_usar(ladron))
    
    mago.definir_trabajo(Habilidad.MAGO)
    
    assertEquals(false,palito.puede_usar(ladron))
    assertEquals(true,palito.puede_usar(mago))
    assertEquals(false,guerrero.validar_ubicacion(Posicion.MANO_DER, new Espada_Zurda))
    //assertEquals(350, guerrero.get_hp_actual)
  }
  
}
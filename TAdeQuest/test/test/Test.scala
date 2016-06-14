package test

import org.junit.Test
import org.junit.Assert._
import tadp.Heroe
import tadp.Posicion

class Tests{

  @Test
  def pruebaInicial():Unit = {
    val guerrero = new Heroe    
    
    //println("HP Base: " + guerrero.stats.hp)
    //println("HP Trabajo: " + guerrero.trabajo.stats.hp)
    //println("HP Actual: " + guerrero.get_hp_actual)        
    
    assertEquals(124, guerrero.get_hp_actual)
  }
  
}
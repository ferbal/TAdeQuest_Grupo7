package test

import org.junit.Test
import org.junit.Assert._
import org.junit.Before
import tadp.Heroe
import tadp.Item
import tadp.Stats
import tadp.Trabajo
import tadp.Posicion
import tadp.Habilidad
import tadp.Guerrero
import tadp.Fuerza
import com.sun.beans.decoder.IntElementHandler
import tadp.Inteligencia
import tadp.Ladron
import tadp.Velocidad
import tadp.Cabeza
import tadp.Mago
import tadp.ManoDer
import tadp.AmbasManos
import tadp.ManoIzq
import tadp.Talismanes

class Tests {
 
  //Cargamos la base de datos de items

    val sin_trabajo = None//new Trabajo(Habilidad.SIN_TRABAJO, Stat_Principal.NINGUNO, {(st,x)=> st})
    
    val guerrero_job = new Trabajo (Guerrero, Fuerza, 
        {(st,x)=>
          st.incrementar(new Stats(10,15,0,-10))
          st 
        })
    val mago_job = new Trabajo (Mago, Inteligencia, 
        {(st,x)=>
          st.incrementar(new Stats(0,-20,0,20))
          st 
        })
    val ladron_job = new Trabajo (Ladron, Velocidad, 
        {(st,x)=>
          st.incrementar(new Stats(-5,0,10,0))
          st 
        })
        
    val Vincha_Bufalo_Agua = new Item(Cabeza,
                                        { (st, x) =>
                                          if (x.stats.fuerza > x.stats.inteligencia) {
                                            st.inteligencia += 30
                                          } else {
                                            st.fuerza += 10
                                            st.hp += 10
                                            st.velocidad += 10
                                          }
                                          st
                                        },{ _.trabajo == None })

  val Palito_Magico = new Item(ManoDer,
    { (st, x) =>
      st.incrementar(new Stats(0, 0, 0, 20))
      st
    },
    { x =>
      x.getTipoTrabajo() == Mago ||
        (x.getTipoTrabajo()  == Ladron &&
          x.stats.inteligencia > 30)
    })
     
  val Casco_Supremo = new Item(Cabeza,
      {(st, x) => st.incrementar(new Stats (x.stats.hp + 100,0,0,0))     
     st},
     {x => true})
     
  val Espada_Doble = new Item(AmbasManos,
      {(st, x)=> st.incrementar(new Stats (50,0,0,0))     
     st},
     {x=>true})
  
  val Espada_Zurda = new Item(ManoIzq,
      {(st, x)=> st.incrementar(new Stats (200,0,0,0))     
     st
   },
   {x=>true})
  
  @Test
  def pruebaOrdenSuperior(): Unit = {
    var warrior = new Heroe
    var theif = new Heroe
    var wizard = new Heroe

    val efecto_loco: (Stats, Heroe) => Stats = { (st, x) =>
      st.hp *= 2
      st.fuerza += 5
      return st
    }
    
    val Casco_Loco = new Item(Cabeza, efecto_loco, { x => true })
    assertEquals(100, warrior.stats.hp)
    warrior = warrior.utilizar_item(Cabeza, Casco_Loco)
    assertEquals(200, warrior.get_stats_actuales().hp)    

  }
  
  @Test
  def pruebaInicial(): Unit = {

    var unGuerrero = new Heroe
    val unPalito = Palito_Magico
    var unLadron = new Heroe
    var unMago = new Heroe
    val unaVincha = Vincha_Bufalo_Agua

    unGuerrero = unGuerrero.utilizar_item(Cabeza, Casco_Supremo)
    .utilizar_item(AmbasManos, Espada_Doble)
    //guerrero.utilizar_item(Posicion.MANO_IZQ, Espada_Zurda)
    .utilizar_item(AmbasManos, Espada_Doble)
    //println("HP Base: " + guerrero.stats.hp)
    //println("HP Trabajo: " + guerrero.trabajo.stats.hp)
    println("HP Actual: " + unGuerrero.get_stats_actuales.hp)
    unGuerrero = unGuerrero.cambiarTrabajoA(Some(guerrero_job))
    println("HP Actual: " + unGuerrero.get_stats_actuales.hp)

    unLadron = unLadron.cambiarTrabajoA(Some(ladron_job))

    assertEquals(false, unaVincha.puede_usar(unLadron))

    unLadron = unLadron.cambiarTrabajoA(sin_trabajo)

    assertEquals(true, unaVincha.puede_usar(unLadron))

    unLadron = unLadron.cambiarTrabajoA(Some(ladron_job))
    
    unMago = unMago.cambiarTrabajoA(Some(mago_job))

    assertEquals(false, unPalito.puede_usar(unLadron))
    //assertEquals(true, unPalito.puede_usar(unMago))
    //assertEquals(false, unGuerrero.validar_ubicacion(ManoDer, Espada_Zurda))
    
    println("HP Actual: " + unGuerrero.get_stats_actuales.hp)
    assertEquals(260, unGuerrero.get_stats_actuales().hp)
    
    //unGuerrero = unGuerrero.utilizar_item(Talismanes, Vincha_Bufalo_Agua)
    
    println("HP Actual: " + unGuerrero.get_stats_actuales.hp)    
    //unGuerrero = unGuerrero.utilizar_item(Talismanes, Vincha_Bufalo_Agua)
  
  }
  
  @Test
  def pruebaDeTrabajos(): Unit = {
    var warrior = new Heroe
    warrior = warrior.cambiarTrabajoA(Some(guerrero_job))
    
  }

}
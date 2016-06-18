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
import tadp.Stat_Principal

class Tests {
 
  //Cargamos la base de datos de items

    val sin_trabajo = new Trabajo(Habilidad.SIN_TRABAJO, Stat_Principal.NINGUNO, {(st,x)=> st})
    val guerrero = new Trabajo (Habilidad.GUERRERO, Stat_Principal.FUERZA, 
        {(st,x)=>
          st.incrementar(new Stats(10,15,0,-10))
          st 
        })
      val mago = new Trabajo (Habilidad.MAGO, Stat_Principal.INTELIGENCIA, 
        {(st,x)=>
          st.incrementar(new Stats(0,-20,0,20))
          st 
        })
      val ladron = new Trabajo (Habilidad.LADRON, Stat_Principal.VELOCIDAD, 
        {(st,x)=>
          st.incrementar(new Stats(-5,0,10,0))
          st 
        })
        
     val Vincha_Bufalo_Agua = new Item(Posicion.CABEZA,
                                        { (st, x) =>
                                          if (x.stats.fuerza > x.stats.inteligencia) {
                                            st.inteligencia += 30
                                          } else {
                                            st.fuerza += 10
                                            st.hp += 10
                                            st.velocidad += 10
                                          }
                                          st
                                        },{ _.trabajo.stat_principal == Stat_Principal.NINGUNO })

  val Palito_Magico = new Item(Posicion.MANO_DER,
    { (st, x) =>
      st.incrementar(new Stats(0, 0, 0, 20))
      st
    },
    { x =>
      x.trabajo.tipo == Habilidad.MAGO ||
        (x.trabajo.tipo == Habilidad.LADRON &&
          x.stats.inteligencia > 30)
    })
     
  val Casco_Supremo = new Item(Posicion.CABEZA,
      {(st, x) => st.incrementar(new Stats (x.stats.hp + 100,0,0,0))     
     st},
     {x => true})
     
  val Espada_Doble = new Item(Posicion.AMBAS_MANOS,
      {(st, x)=> st.incrementar(new Stats (50,0,0,0))     
     st},
     {x=>true})
  
  val Espada_Zurda = new Item(Posicion.MANO_IZQ,
      {(st, x)=> st.incrementar(new Stats (200,0,0,0))     
     st
   },
   {x=>true})
  
  @Test
  def pruebaOrdenSuperior(): Unit = {
    val warrior = new Heroe
    val theif = new Heroe
    val wizard = new Heroe

    val efecto_loco: (Stats, Heroe) => Stats = { (st, x) =>
      st.hp *= 2
      st.fuerza += 5
      return st
    }
    
    val Casco_Loco = new Item(Posicion.CABEZA, efecto_loco, { x => true })
    warrior.utilizar_item(Posicion.CABEZA, Casco_Loco)
    assertEquals(100, warrior.stats.hp)
    assertEquals(200, warrior.get_stats_actuales().hp)    

  }
  
  @Test
  def pruebaInicial(): Unit = {

    val unGuerrero = new Heroe
    val unPalito = Palito_Magico
    val unLadron = new Heroe
    val unMago = new Heroe
    val unaVincha = Vincha_Bufalo_Agua

    unGuerrero.utilizar_item(Posicion.CABEZA, Casco_Supremo)
    unGuerrero.utilizar_item(Posicion.AMBAS_MANOS, Espada_Doble)
    //guerrero.utilizar_item(Posicion.MANO_IZQ, Espada_Zurda)
    unGuerrero.utilizar_item(Posicion.AMBAS_MANOS, Espada_Doble)
    //println("HP Base: " + guerrero.stats.hp)
    //println("HP Trabajo: " + guerrero.trabajo.stats.hp)
    println("HP Actual: " + unGuerrero.get_stats_actuales.hp)
    unGuerrero.cambiarTrabajoA(guerrero)
    println("HP Actual: " + unGuerrero.get_stats_actuales.hp)

    unLadron.cambiarTrabajoA(ladron)

    assertEquals(false, unaVincha.puede_usar(unLadron))

    unLadron.cambiarTrabajoA(sin_trabajo)

    assertEquals(true, unaVincha.puede_usar(unLadron))

    unMago.cambiarTrabajoA(mago)

    assertEquals(false, unPalito.puede_usar(unLadron))
    assertEquals(true, unPalito.puede_usar(unMago))
    assertEquals(false, unGuerrero.validar_ubicacion(Posicion.MANO_DER, Espada_Zurda))
    
    assertEquals(360, unGuerrero.get_stats_actuales().hp)
    
    unGuerrero.utilizar_item(Posicion.TALISMANES, Vincha_Bufalo_Agua)
    
    println("HP Actual: " + unGuerrero.get_stats_actuales.hp)
    unGuerrero.utilizar_item(Posicion.TALISMANES, Vincha_Bufalo_Agua)
    
  }
  
  @Test
  def pruebaDeTrabajos(): Unit = {
    var warrior = new Heroe
    warrior.cambiarTrabajoA(guerrero)
    
  }
}
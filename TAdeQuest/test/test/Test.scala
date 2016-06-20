package test

import org.junit.Test
import org.junit.Assert._
import org.junit.Before
import tadp.Heroe
import tadp.Item
import tadp.Stats
import tadp.Trabajo
import tadp.Equipo
import tadp.Tarea
import tadp.Mision
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
          
        })
    val mago_job = new Trabajo (Mago, Inteligencia, 
        {(st,x)=>
          st.incrementar(new Stats(0,-20,0,20))
           
        })
    val ladron_job = new Trabajo (Ladron, Velocidad, 
        {(st,x)=>
          st.incrementar(new Stats(-5,0,10,0))
           
        })
        
    val Vincha_Bufalo_Agua = new Item(Cabeza,
                                        { (st, x) =>
                                          if (x.stats.fuerza > x.stats.inteligencia) {
                                            st.incrementar(0,0,0,30)
                                          } else {
                                            st.incrementar(10,10,10,0)
                                          }
                                          
                                        },{ _.trabajo == None })

  val Palito_Magico = new Item(ManoDer,
    { (st, x) =>
      st.incrementar(new Stats(0, 0, 0, 20))
    },
    { x =>
      x.getTipoTrabajo() == Mago ||
        (x.getTipoTrabajo()  == Ladron &&
          x.stats.inteligencia > 30)
    })
     
  val Casco_Supremo = new Item(Cabeza,
      {(st, x) => st.incrementar(new Stats (x.stats.hp + 100,0,0,0))     
     },
     {x => true})
     
  val Espada_Doble = new Item(AmbasManos,
      {(st, x)=> st.incrementar(new Stats (50,0,0,0))     
     },
     {x=>true})
  
  val Espada_Zurda = new Item(ManoIzq,
      {(st, x)=> st.incrementar(new Stats (200,0,0,0))     
     
   },
   {x=>true})
    
  val pelearMonstruo = new Tarea(
      {x=> if(x.get_stats_actuales.fuerza < 20)
      { x.copy(stats = x.stats.incrementar(new Stats(-20,0,0,0)))}
      else {x.copy(stats = x.stats.incrementar(new Stats(-10,0,0,0)))}},
      {(x,y)=> if(x.lider.trabajo.get.tipo == Guerrero){20} else {10}})
    
  @Before
  def initialize(){
    var conan = Heroe().cambiarTrabajoA(Some(guerrero_job))
    var robinHood = Heroe().cambiarTrabajoA(Some(ladron_job))
    var gandalf = Heroe().cambiarTrabajoA(Some(mago_job))
    var equipo = Equipo(List[Heroe](conan, robinHood, gandalf), 100, "Dream Team")}
  
  @Test
  def pruebaTareaExitosa(){
    
  }
  
  @Test
  def pruebaTareaFallida(){}
  
  @Test
  def pruebaMisionExitosa(){}
  
  @Test
  def pruebaMisionFallida(){}
  
    
  @Test
  def pruebaOrdenSuperior(): Unit = {
    var warrior = new Heroe
    var theif = new Heroe
    var wizard = new Heroe

    val efecto_loco: (Stats, Heroe) => Stats = { (st, x) =>
      st.copy(hp = st.hp * 2)
      st.copy(fuerza = st.fuerza + 5)
      return st
    }
    
    val Casco_Loco = new Item(Cabeza, efecto_loco, { x => true })
    assertEquals(100, warrior.stats.hp)
    warrior = warrior.utilizar_item(Cabeza, Casco_Loco)
    assertEquals(200, warrior.get_stats_actuales().hp)    

  }
    
  @Test
  def pruebaInicial(): Unit = {

    var unGuerrero =  Heroe()
    val unPalito = Palito_Magico
    var unLadron =  Heroe()
    var unMago =  Heroe()
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
    assertEquals(360, unGuerrero.get_stats_actuales().hp)
    
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
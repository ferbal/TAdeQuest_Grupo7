package test

import org.junit.Test
import org.junit.Assert._
import org.junit.Before
import tadp.Heroe
import tadp.Item
import tadp.Stats
import tadp.Posicion
//import tadp.Casco_Supremo
//import tadp.Casco
//import tadp.Espada_Doble
//import tadp.Espada_Zurda
//import tadp.Espada_Zurda
//import tadp.Espada_Doble
//import tadp.Palito_Magico
import tadp.Habilidad
//import tadp.Vincha_Bufalo_Agua

class Tests {

//Cargamos la base de datos de items
  
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
    },
    { _.trabajo.stat_principal == "Sin Trabajo" })

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
    val guerrero = new Heroe
    val ladron = new Heroe
    val mago = new Heroe

    val efecto_loco: (Stats, Heroe) => Stats = { (st, x) =>
      st.hp *= 2
      st.fuerza += 5
      return st
    }

    val Casco_Loco = new Item(Posicion.CABEZA, efecto_loco, { x => true })
    guerrero.utilizar_item(Posicion.CABEZA, Casco_Loco)
    assertEquals(100, guerrero.stats.hp)
    assertEquals(200, guerrero.get_stats_actuales().hp)

  }

  @Test
  def pruebaInicial(): Unit = {

    val guerrero = new Heroe
    val palito = Palito_Magico
    val ladron = new Heroe
    val mago = new Heroe
    val vincha = Vincha_Bufalo_Agua

    guerrero.utilizar_item(Posicion.CABEZA, Casco_Supremo)
    guerrero.utilizar_item(Posicion.AMBAS_MANOS, Espada_Doble)
    guerrero.utilizar_item(Posicion.MANO_IZQ, Espada_Zurda)
    guerrero.utilizar_item(Posicion.AMBAS_MANOS, Espada_Doble)
    //println("HP Base: " + guerrero.stats.hp)
    //println("HP Trabajo: " + guerrero.trabajo.stats.hp)
    println("HP Actual: " + guerrero.get_stats_actuales.hp)
    guerrero.definir_trabajo(Habilidad.GUERRERO)
    println("HP Actual: " + guerrero.get_stats_actuales.hp)

    ladron.definir_trabajo(Habilidad.LADRON)

    assertEquals(false, vincha.puede_usar(ladron))

    ladron.definir_trabajo(Habilidad.SIN_TRABAJO)

    assertEquals(true, vincha.puede_usar(ladron))

    mago.definir_trabajo(Habilidad.MAGO)

    assertEquals(false, palito.puede_usar(ladron))
    assertEquals(true, palito.puede_usar(mago))
    assertEquals(false, guerrero.validar_ubicacion(Posicion.MANO_DER, Espada_Zurda))
    //assertEquals(350, guerrero.get_hp_actual)
  }
}
package test

import org.junit.Test
import org.junit.Assert._
import org.junit.Before
import org.junit.Rule
import org.junit.rules.ExpectedException
import scala.annotation.meta.getter
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
import tadp.Palito_Magico
import tadp.Vincha_Bufalo_Agua
import tadp.Talisman_Comun
import tadp.Casco_Supremo
import tadp.Espada_Doble
import tadp.Espada_Zurda
import tadp.Guerrero_Job
import tadp.Mago_Job
import tadp.Ladron_Job
import scala.util.Success
import tadp.Talisman_Maldito
import tadp.Taberna

class Tests {

  //Cargamos la base de datos de items

  val sin_trabajo = None // Trabajo(Habilidad.SIN_TRABAJO, Stat_Principal.NINGUNO, {(st,x)=> st})
  val guerrero_job = Guerrero_Job()
  val mago_job = Mago_Job()
  val ladron_job = Ladron_Job()

  var conan = Heroe()
  var vago = Heroe()
  var vagabundo = Heroe()
  var robinHood = Heroe()
  var gandalf = Heroe()
  var equipo = Equipo()
  var equipoVago = Equipo()

  val unaVinchaBufaloAgua = Vincha_Bufalo_Agua()
  val unPalitoMagico = Palito_Magico()
  val unTalisman = Talisman_Comun()
  val unCascoSupremo = Casco_Supremo()
  val unaEspadaDoble = Espada_Doble()
  val unaEspadaZurda = Espada_Zurda()
  val unTalismanMaldito = Talisman_Maldito()

  val pelearMonstruo =  new Tarea(
    { x =>
      if (x.get_stats_actuales.fuerza < 20) { x.copy(stats = x.stats.incrementar( Stats(-20, 0, 0, 0))) }
      else { x.copy(stats = x.stats.incrementar( Stats(-10, 0, 0, 0))) }
    },
    { (x, y) => if (x.lider.trabajo.get.tipo == Guerrero) { Success(20) } else { Success(10) } })
  
val pelearMonstruoParaLadrones =  new Tarea(
    { x =>
      if (x.get_stats_actuales.fuerza < 20) { x.copy(stats = x.stats.incrementar( Stats(-20, 0, 0, 0))) }
      else { x.copy(stats = x.stats.incrementar( Stats(-10, 0, 0, 0))) }
    },
    { (x, y) => if (y.trabajo.get.tipo == Ladron) { Success(20) } else { Success(10) } })
  
  val tareaExploraBosqueEncantado = new Tarea ({ x => x.copy(stats = x.stats.incrementar( Stats(-10, -10, +20, +30))) },
      {(x, y) => if (y.getTipoTrabajo == Mago) {Success(y.get_stats_actuales.inteligencia*2)} else {Success(y.get_stats_actuales.velocidad)}})

  val tareaExploraPrisionInfernal = new Tarea ({x => x.utilizar_item(Talismanes,unTalisman)},
      {(x, y)=> if(x.lider.trabajo.get.tipo == Ladron){Success(y.get_stats_actuales().inteligencia)}else{Success(1)}})
  
  val tareaImposible =  new Tarea(
    { x => x.copy(stats = x.stats.incrementar( Stats(-20, -10, -10, -30))) },
    { (x, y) =>
      for {
        st <- x.lider.get_stat_principal
        res = 10 if st > 100
      } yield (res)
    })

  val misionAntiMonstruo = new Mision(
    List[Tarea](pelearMonstruo),
    { x =>
      for{
        eq <- x
        resEq = eq.copy(oro = eq.oro * 2)
    }yield(resEq)})
  
  val misionExploradora = new Mision(
      List(tareaExploraBosqueEncantado, tareaExploraPrisionInfernal),
      { x =>
      for{
        eq <- x
        resEq <- eq.obtenerItem(unTalismanMaldito)
    }yield(resEq)})
      
  val tabernaPosible = new Taberna()
  tabernaPosible.agregarMision(misionExploradora)
  tabernaPosible.agregarMision(misionAntiMonstruo)
  
  val tabernaImposible = new Taberna()

  @Before
  def initialize() {
    conan = Heroe().cambiarTrabajoA(Some(guerrero_job))
    robinHood = Heroe().cambiarTrabajoA(Some(ladron_job))
    gandalf = Heroe().cambiarTrabajoA(Some(mago_job))
    vago = Heroe().cambiarTrabajoA(None)
    vagabundo = Heroe().cambiarTrabajoA(None)
    equipo = Equipo(List[Heroe](conan, robinHood, gandalf), 100, "Dream Team")
    equipoVago = Equipo(List[Heroe](vago, vagabundo), 100, "Straw Team")
    
  }

  @Test
  def pruebaTareaExitosa() {
      val res = pelearMonstruoParaLadrones.realizarTarea(equipo)
      val ladronPostPelea =  robinHood.copy(stats = robinHood.stats.incrementar( Stats(-10, 0, 0, 0)))
      println("Stats ladron: " ++ ladronPostPelea.get_stats_actuales().hp.toString)
      println("Stats Equipo: ")
      res.get.heroes.foreach { x => print(x.get_stats_actuales().hp.toString()++"/") }
      println
      assertTrue(res.get.contieneEsteHeroe(ladronPostPelea))
  }
  @Test
  def testEquipoContieneUnHeroe() {
    val asd = Heroe().cambiarTrabajoA(Some(ladron_job))
   assertTrue( equipo.contieneEsteHeroe(asd))
  }

  @Test
  def pruebaTareaFallida() {
    try {
      tareaImposible.realizarTarea(equipo).get
      fail("no se produjo la excepcion esperada")
    } catch {
      case e: Exception => assertEquals("No hay ningun heroe capaz de realizar la tarea", e.getMessage)
    }
  }

  @Test
  def pruebaMisionExitosa() {
    var res =for(eq <- misionAntiMonstruo.realizarMision(equipo))yield(eq)
    assertEquals(200, res.get.oro)
  }

  @Test
  def pruebaMisionFallida() {}

  @Test
  def pruebaOrdenSuperior(): Unit = {
    var warrior = Heroe()
    var theif = Heroe()
    var wizard = Heroe()

    val efecto_loco: (Stats, Heroe) => Stats = { (st, x) =>
      st.copy(hp = st.hp * 2)
      st.copy(fuerza = st.fuerza + 5)
      return st
    }

    val Casco_Loco =  new Item(Cabeza, efecto_loco, { x => true })
    assertEquals(100, warrior.stats.hp)
    warrior = warrior.utilizar_item(Cabeza, Casco_Loco)
    assertEquals(200, warrior.get_stats_actuales().hp)

  }

  @Test
  def pruebaValidacionUsoItems(): Unit = {

    var unLadron = Heroe()
    var unMago = Heroe()

    unLadron = unLadron.cambiarTrabajoA(Some(ladron_job))

    assertEquals(false, unaVinchaBufaloAgua.puede_usar(unLadron))

    unLadron = unLadron.cambiarTrabajoA(sin_trabajo)

    assertEquals(true, unaVinchaBufaloAgua.puede_usar(unLadron))

    try {
      unPalitoMagico.puede_usar(unLadron)
      fail("No se produjo la excepcion esperada")
    } catch {
      case e: Exception => assertEquals("No tiene trabajo asignado", e.getMessage)
    }

  }

  @Test
  def PruebaCambiosDeItemsYTrabajos(): Unit = {

    //Un Heroe NUEVO tiene un HP Base de 100  
    var unGuerrero = Heroe()
    assertEquals(100, unGuerrero.get_stats_actuales().hp)

    //El trabajo GUERRERO le asigna +10 HP
    unGuerrero = unGuerrero.cambiarTrabajoA(Some(guerrero_job))
    assertEquals(110, unGuerrero.get_stats_actuales().hp)

    //El CASCO_SUPREMO le incrementa (el HP base + 100)
    unGuerrero = unGuerrero.utilizar_item(Cabeza, unCascoSupremo)

    assertEquals(310, unGuerrero.get_stats_actuales().hp)

    //La Espada_Doble incrementa 50 unidades del HP
    unGuerrero = unGuerrero.utilizar_item(AmbasManos, unaEspadaDoble)
    assertEquals(360, unGuerrero.get_stats_actuales().hp)

    //La Espada Zurda incrementa en 200 el HP. Desafectando la Espada Doble (restando los 50 de la ultima)
    unGuerrero = unGuerrero.utilizar_item(AmbasManos, unaEspadaZurda)
    assertEquals(510, unGuerrero.get_stats_actuales().hp)

    //Volvemos a Asignar la Espada_Doble
    unGuerrero = unGuerrero.utilizar_item(AmbasManos, unaEspadaDoble)
    assertEquals(360, unGuerrero.get_stats_actuales().hp)

    //Dejar al Heroe sin trabajo reduce los 10 HP incrementado como Guerrero
    unGuerrero = unGuerrero.cambiarTrabajoA(None)
    assertEquals(350, unGuerrero.get_stats_actuales().hp)

    //Incorporo un Talisman Suma 50 HP
    unGuerrero = unGuerrero.utilizar_item(Talismanes, unTalisman)
    assertEquals(360, unGuerrero.get_stats_actuales().hp)

  }

  @Test
  def pruebaEligeEntreMisiones{
    //La mision exploradora vende el item, mientras que la otra duplica su oro, pero el equipo empieza con 0 de oro
    assertEquals(misionExploradora, tabernaPosible.elegirMision(equipo, {(x,y)=> println("Oro mision: "++ y.oro.toString)
      x.oro < y.oro}))
  }
  
  @Test
  def pruebaEligeMisionPosibleAnteUnaPosibleYUnaImposible{
    
  }

}
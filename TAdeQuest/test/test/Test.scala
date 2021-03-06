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
import tadp.Resultado
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
import scala.util.Failure
import tadp.Exito
import tadp.Fallo

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
    { (x, y) => if (x.lider.get.trabajo.get.tipo == Guerrero) { Some(20) } else { Some(10) } })
  
val pelearMonstruoParaLadrones =  new Tarea(
    { x =>
      if (x.get_stats_actuales.fuerza < 20) { x.copy(stats = x.stats.incrementar( Stats(-20, 0, 0, 0))) }
      else { x.copy(stats = x.stats.incrementar( Stats(-10, 0, 0, 0))) }
    },
    { (x, y) => if (y.trabajo.get.tipo == Ladron) { Some(20) } else { Some(10) } })
  
  val tareaExploraBosqueEncantado = new Tarea ({ x => x.copy(stats = x.stats.incrementar( Stats(-10, -10, +20, +30))) },
      {(x, y) => if (y.getTipoTrabajo == Mago) {Some(y.get_stats_actuales.inteligencia*2)} else {Some(y.get_stats_actuales.velocidad)}})

  val tareaExploraPrisionInfernal = new Tarea ({x => x.utilizar_item(Talismanes,unTalisman)},
      {(x, y)=> if(x.lider.get.trabajo.get.tipo == Ladron){Some(y.get_stats_actuales().inteligencia)}else{Some(1)}})
  
  val tareaImposible =  new Tarea(
    { x => x.copy(stats = x.stats.incrementar( Stats(-20, -10, -10, -30))) },
    { (x, y) =>
      for {
        st <- x.lider.get.get_stat_principal
        res = 10 if st > 1000
      } yield (res)
    })


  val misionAntiMonstruo = new Mision(
    List[Tarea](pelearMonstruo),
    { x => x.copy(oro = x.oro * 2)}
    )
  
  val misionExploradora = new Mision(
      List(tareaExploraBosqueEncantado, tareaExploraPrisionInfernal),
      { x => x.obtenerItem(unTalismanMaldito)})
  
  val misionImposible = Mision(
    List[Tarea](tareaImposible),
    { x => x.copy(oro = 1000 + (x.oro * 2))})
      
  val tabernaPosible = new Taberna()
  tabernaPosible.agregarMision(misionExploradora)
  tabernaPosible.agregarMision(misionAntiMonstruo)
  
  val tabernaImposible = new Taberna()
  tabernaImposible.agregarMision(misionExploradora)
  tabernaImposible.agregarMision(misionImposible)

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
      res.equipo.heroes.foreach { x => print(x.get_stats_actuales().hp.toString()++"/") }
      println
      assertTrue(res.equipo.contieneEsteHeroe(ladronPostPelea))
  }
  @Test
  def testEquipoContieneUnHeroe() {
    val asd = Heroe().cambiarTrabajoA(Some(ladron_job))
   assertTrue( equipo.contieneEsteHeroe(asd))
  }

  @Test
  def pruebaTareaFallida() {
    
      assertEquals(Fallo(equipo, tareaImposible), tareaImposible.realizarTarea(equipo))
    
  }

  @Test
  def pruebaMisionExitosa() {
    var res =for(eq <- misionAntiMonstruo.realizarMision(equipo))yield(eq)
    assertEquals(200, res.equipo.oro)
  }

  @Test
  def pruebaMisionFallida() {
    misionImposible.realizarMision(equipo) match{
      case Exito(x) => fail("no se produjo la excepcion esperada")
      case Fallo(x, y) => assertEquals(tareaImposible, y)
    }
  }

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


    assertEquals(false,  unPalitoMagico.puede_usar(unLadron))


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
    //La mision exploradora vende el item (100+20), mientras que la otra duplica su oro(200)
    assertEquals(Some(misionAntiMonstruo), tabernaPosible.elegirMision(equipo, {(x,y)=> println("Oro despues de mision: "++ x.oro.toString)
      x.oro > y.oro}))
  }
  
  @Test
  def pruebaEligeMisionPosibleAnteUnaPosibleYUnaImposible{
    //no importa la condicion, siempre se elige la mision que puede ser completada
    assertEquals(Some(misionExploradora), tabernaImposible.elegirMision(equipo, {(x,y)=> x.oro < y.oro}))
    assertEquals(Some(misionExploradora), tabernaImposible.elegirMision(equipo, {(x,y)=> x.oro > y.oro}))
  }
  
  @Test 
  def pruebaEntrenar{
    assertEquals(220, tabernaPosible.entrenar(equipo, {(x,y)=> x.oro > y.oro}).equipo.oro)
    assertEquals(240, tabernaPosible.entrenar(equipo, {(x,y)=> x.oro < y.oro}).equipo.oro)
  }
  
  @Test
  def pruebaEntrenarHastaFallar{
    assertEquals(120, tabernaImposible.entrenar(equipo, {(x,y)=> x.oro > y.oro}).equipo.oro)
  }

}
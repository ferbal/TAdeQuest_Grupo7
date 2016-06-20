package tadp

import scala.util.control.Exception

case class Equipo(heroes: List[Heroe] = List[Heroe](), oro: Int, nombre: String) {

  def mejorHeroeSegun(f: Heroe => Int): List[Heroe] = {
    try {
      heroes match {
        case x :: Nil => x :: Nil
        case x :: xs =>
          val results = heroes.map { x => f(x) }
          val max = results.reduceLeft(_ max _)
          heroes.filter { x => f(x) == max }
        case Nil => Nil
      }
    } catch {
      case _ => Nil
    }
  }

  def obtenerItem(item: Item): Equipo = {
    mejorHeroeSegun({ x =>
      var statnuevo = x.utilizar_item(item.ubicacion, item).get_stat_principal
      statnuevo -= x.get_stat_principal
      if (statnuevo > 0) { statnuevo }
      else { throw new Exception("No hay heroe al que le convenga equiparse el item") }
    }) match {
      case Nil     => this //no hay heroes aptos para equiparse el item, por lo que se descarta
      case x :: xs => reemplazarMiembro(x, x.utilizar_item(item.ubicacion, item))
    }
  }

  def obtenerMiembro(heroe: Heroe): Equipo = {
    copy(heroes = heroe :: heroes)
  }

  def reemplazarMiembro(reemplazado: Heroe, reemplazo: Heroe): Equipo = {
    copy(heroes.updated(heroes.indexOf(reemplazado), reemplazo))
  }

  def lider: Heroe = {
    mejorHeroeSegun({ x => x.get_stat_principal }) match {
      case x :: Nil => x
      case x :: xs  => throw new Exception("Hay mas de un Lider, por lo tanto nadie es Lider")
      case Nil      => throw new Exception("No hay Lider")
    }
  }
}
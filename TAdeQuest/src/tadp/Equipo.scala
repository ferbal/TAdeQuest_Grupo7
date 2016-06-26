package tadp

import scala.util.control.Exception
import scala.util.Success
import scala.util.Try
import scala.util.Failure

case class Equipo(heroes: List[Heroe] = List[Heroe](), oro: Int = 0, nombre: String = "Equipo") {

  def mejorHeroeSegun(f: Heroe => Option[Int]): List[Heroe] = {
    heroes match {
      case x :: Nil => x :: Nil
      case x :: xs =>
        val results = heroes.map { x => f(x) }
        val max = results.reduceLeft({ (x, y) =>
          for {
            x1 <- x
            y1 <- y
          } yield (x1 max y1)
        })
        heroes.filter { x => f(x) == max }
      case Nil => Nil
    }
  }

  def obtenerItem(item: Item): Equipo = {
      mejorHeroeSegun({ x =>
        var statnuevo = x.utilizar_item(item.ubicacion, item).get_stat_principal
        for {
          stn <- statnuevo
          st <- x.get_stat_principal
          res = (stn - st) if (stn-st > 0)
        } yield { res }
      }) match {
        case Nil => this.copy( oro = this.oro + 20)
        case x :: xs => { reemplazarMiembro(x, x.utilizar_item(item.ubicacion, item)) }
      }
  }

  def obtenerMiembro(heroe: Heroe): Equipo = {
    copy(heroes = heroe :: heroes)
  }
  def contieneEsteHeroe(heroe: Heroe) = {
    heroes.contains(heroe)
  }
  def reemplazarMiembro(viejo: Heroe, nuevo: Heroe) = {
    var nuevaLista = heroes.diff(List(viejo))
    copy(heroes = nuevo :: nuevaLista)
  }
  // def reemplazarMiembro(reemplazado: Heroe, reemplazo: Heroe): Equipo = {
  //   copy(heroes.updated(heroes.indexOf(reemplazado), reemplazo))
  // }

  def lider: Option[Heroe] = {
    mejorHeroeSegun({ x => for (st <- x.get_stat_principal) yield (st)

    }) match {
      case x :: Nil => Some(x)
      case x :: xs  => None
      case Nil      => None
    }
  }
 
}
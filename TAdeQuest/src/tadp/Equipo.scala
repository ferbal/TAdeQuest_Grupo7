package tadp

import scala.util.control.Exception
import scala.util.Success
import scala.util.Try
import scala.util.Failure

case class Equipo(heroes: List[Heroe] = List[Heroe](), oro: Int = 0, nombre: String = "Equipo") {

  def mejorHeroeSegun(f: Heroe => Try[Int]): List[Heroe] = {
      heroes match {
        case x :: Nil => x :: Nil
        case x :: xs =>
          val results = heroes.map { x => f(x) }
          val max = results.reduceLeft({(x,y)=> 
            for{x1<-x
              y1<-y}
            yield(x1 max y1)})
          heroes.filter { x => f(x) == max }
        case Nil => Nil
      }
  }

  def obtenerItem(item: Item): Try[Equipo] = {
    Try(
      mejorHeroeSegun({ x =>
        var statnuevo = x.utilizar_item(item.ubicacion, item).get_stat_principal
        val res = for {
          stn <- statnuevo
          st <- x.get_stat_principal
        } yield { stn - st }
        res match {
          case Success(x) => x
          case Failure(e) => throw e
        }

      }) match {
        case Nil     => this //no hay heroes aptos para equiparse el item, por lo que se descarta
        case x :: xs => reemplazarMiembro(x, x.utilizar_item(item.ubicacion, item))
      })
  }

  def obtenerMiembro(heroe: Heroe): Equipo = {
    copy(heroes = heroe :: heroes)
  }

  def reemplazarMiembro(reemplazado: Heroe, reemplazo: Heroe): Equipo = {
    copy(heroes.updated(heroes.indexOf(reemplazado), reemplazo))
  }

  def lider: Heroe = {
    mejorHeroeSegun({ x => 
      val res = for(st <- x.get_stat_principal) yield (st)
      res match{
        case Success(x) => x
        case Failure(e) => throw e
      }
      })
      match {
      case x :: Nil => x
      case x :: xs  => throw new Exception("Hay mas de un Lider, por lo tanto nadie es Lider")
      case Nil      => throw new Exception("No hay Lider")
    }
  }
}
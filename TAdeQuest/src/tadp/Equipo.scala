package tadp

import scala.util.control.Exception
import scala.util.Success
import scala.util.Try
import scala.util.Failure

case class Equipo(heroes: List[Heroe] = List[Heroe](), oro: Int = 0, nombre: String = "Equipo") {

  def mejorHeroeSegun(f: Heroe => Option[Int]): Option[Heroe] = {
    heroes match {
      case x :: Nil => Some(x) 
      case x :: xs =>
        //val results = heroes.map(x=>f(x))
        val results = for ( unHeroe <- heroes
            )yield Tuple2(unHeroe,f(unHeroe))
        val max = results.filter(!_._2.isEmpty).sortWith( _._2.getOrElse(0) > _._2.getOrElse(0))
        if (max.isEmpty || results.filter(_._1 == max).size > 1)
          None
        else
          Some(results.filter(!_._2.isEmpty).sortWith( _._2.getOrElse(0) > _._2.getOrElse(0)).head._1)        
      case Nil => None      
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
        case None => this.copy( oro = this.oro + 20)
        case Some(x) => { reemplazarMiembro(x, x.utilizar_item(item.ubicacion, item)) }
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

    }) 
//    match {
//      case x :: Nil => Some(x)
//      case x :: xs  => None
//      case Nil      => None
//    }
  }
 
}
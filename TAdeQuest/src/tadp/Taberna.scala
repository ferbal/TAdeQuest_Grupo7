package tadp

import scala.util.Try
import scala.util.Failure
import scala.util.Success

class Taberna {

  var misiones: List[Mision] = List[Mision]()

  type TuplaDeEquipos = (Try[Equipo], Try[Equipo])

  def agregarMision(mision: Mision) = {
    misiones = mision :: misiones
  }

  def mejorMisionSegunCriterio(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean, mision1: Option[Mision], mision2: Option[Mision]): Option[Mision] = {

    var equiposResultantes = (mision1.map { _.realizarMision(equipo) }, mision2.map { _.realizarMision(equipo) })
    equiposResultantes match {
      case (Some(Exito(eq1)), Some(Exito(eq2))) =>
        criterio(eq1, eq2) match {
          case true  => mision1
          case false => mision2
        }
      case (_, Some(Exito(_))) => mision2
      case (Some(Exito(_)), _) => mision1
      case (_, _)              => None
    }

  }

  def elegirMision(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean, misionesRestantes: List[Mision]): Option[Mision] = {
    val x: List[Option[Mision]] = misionesRestantes.map { Some(_) }
    x.reduceLeft({ (x, y) => mejorMisionSegunCriterio(equipo, criterio, x, y) })
  }

  def elegirMision(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Option[Mision] = {
    elegirMision(equipo, criterio, misiones)
  }

  def entrenar(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Resultado = {
    entrenar(equipo, criterio, misiones)
  }

  def entrenar(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean, misionesRestantes: List[Mision]): Resultado = {
    val elegida = elegirMision(equipo, criterio, misionesRestantes)
    elegida match {
      case None => Fallo(equipo, ???)
      case Some(x) => val restantes = misionesRestantes.diff(List(x))
      x.realizarMision(equipo) match {
        case Fallo(_, y) => Fallo(equipo, y)
        case Exito(x) => restantes match {
          case Nil  => Exito(x)
          case list => entrenar(x, criterio, list)
        }
      }
    }
  }

}
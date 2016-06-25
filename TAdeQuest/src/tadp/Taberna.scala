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

  def mejorMisionSegunCriterio(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean, mision1: Try[Mision], mision2: Try[Mision]): Try[Mision] = {

    var equiposResultantes = (mision1.get.realizarMision(equipo), mision2.get.realizarMision(equipo))
    equiposResultantes match {
      case (Failure(_), Success(eq)) => mision2
      case (Success(eq), Failure(_)) => mision1
      case (Failure(ex), Failure(_)) => throw new TodasLasMisionesFallaronException()
      case (Success(eq1), Success(eq2)) =>
        criterio(eq1, eq2) match {
          case true  => mision1
          case false => mision2
        }
    }

  }

  def elegirMision(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean, misionesRestantes: List[Mision]): Mision = {

    var listaDeTryDeMisiones: List[Try[Mision]] = misionesRestantes.map { x => Success(x) }
    var res = listaDeTryDeMisiones.reduceLeft({ (x, y) => mejorMisionSegunCriterio(equipo, criterio, x, y) })
    res.get

  }

  def elegirMision(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Mision = {
    elegirMision(equipo, criterio, misiones)
  }

  def entrenar(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean): Equipo = {
    entrenar(equipo, criterio, misiones)
  }

  def entrenar(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean, misionesRestantes: List[Mision]): Equipo = {
    val elegida = elegirMision(equipo, criterio, misionesRestantes)
    val restantes = misionesRestantes.diff(List(elegida))
    elegida.realizarMision(equipo) match {
      case Failure(e) => equipo
      case Success(x) => restantes match {
        case Nil => x
        case list  => entrenar(x, criterio, list)
      }
    }

  }

}
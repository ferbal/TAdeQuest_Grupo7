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

  def mejorMisionSegunCriterio(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean, mision1: Mision, mision2: Mision): Mision = {

    var equiposResultantes = (mision1.resultadoMision(equipo), mision2.resultadoMision(equipo))
    equiposResultantes match {
      case (Fallo(_,_), Exito(_)) => mision2
      case (Exito(_), Fallo(_,_)) => mision1
      case (Fallo(_,_), Fallo(_,_)) => throw new TodasLasMisionesFallaronException()
      case (Exito(eq1), Exito(eq2)) =>
        criterio(eq1, eq2) match {
          case true  => mision1
          case false => mision2
        }
    }

  }

  def elegirMision(equipo: Equipo, criterio: (Equipo, Equipo) => Boolean, misionesRestantes: List[Mision]): Mision = {

    misionesRestantes.reduceLeft({ (x, y) => mejorMisionSegunCriterio(equipo, criterio, x, y) })
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
    elegida.resultadoMision(equipo) match {
      case Fallo(_,_) => equipo
      case Exito(x) => restantes match {
        case Nil => x
        case list  => entrenar(x, criterio, list)
      }
    }

  }

}
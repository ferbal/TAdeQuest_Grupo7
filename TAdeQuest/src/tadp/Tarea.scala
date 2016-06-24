package tadp

import scala.util.Try

class Tarea(consecuencias: Heroe => Heroe, facilidad: (Equipo, Heroe) => Try[Int]) {

  def realizarTarea(equipo: Equipo): Equipo = {

    equipo.mejorHeroeSegun { x => facilidad(equipo, x) } match {
      case x :: xs => equipo.reemplazarMiembro(x, consecuencias(x))
      case Nil     => throw new Exception("No hay ningun heroe capaz de realizar la tarea")
    }
  }
}
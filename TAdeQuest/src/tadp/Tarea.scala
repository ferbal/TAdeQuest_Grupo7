package tadp

import scala.util.Try

class Tarea(consecuencias: Heroe => Heroe, facilidad: (Equipo, Heroe) => Try[Int]) {

  
  def realizarTareaRecibeTry(equipo: Try[Equipo]): Try[Equipo] = {
    for{
      eq <- equipo
      nuevoeq <- realizarTarea(eq)
    }yield(nuevoeq)
  }
  def realizarTarea(equipo: Equipo): Try[Equipo] = {

    

    Try(
    equipo.mejorHeroeSegun { x => facilidad(equipo, x) } match {
      case x :: xs => equipo.reemplazarMiembro(x, consecuencias(x))
      case Nil     => throw new Exception("No hay ningun heroe capaz de realizar la tarea")
    })
  }
}
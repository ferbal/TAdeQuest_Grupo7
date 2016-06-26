package tadp

import scala.util.Try

class Tarea(consecuencias: Heroe => Heroe, facilidad: (Equipo, Heroe) => Option[Int]) {

  def realizarTarea(equipo: ResultadoTarea): ResultadoTarea = {
    for {
      eq <- equipo
      nuevoeq <- realizarTarea(eq)
    } yield (nuevoeq)
  }
  
  def realizarTarea(equipo: Equipo): ResultadoTarea = {
      equipo.mejorHeroeSegun { x => facilidad(equipo, x) } match {
        case x :: xs => Exito(equipo.reemplazarMiembro(x, consecuencias(x)), this)
        case Nil     => Fallo(equipo, this)
      }
  }
}

trait ResultadoTarea {
  def equipo: Equipo
  def tarea: Tarea
  def map(f: (Equipo => Equipo)): ResultadoTarea
  def filter(f: (Equipo => Boolean)): ResultadoTarea
  def flatMap(f: (Equipo => ResultadoTarea)): ResultadoTarea
  def fold[T](e: (Equipo => T))(f: (Equipo => T)): T
  }

case class Exito(val equipo:Equipo, val tarea: Tarea) extends ResultadoTarea{
  def map(f: (Equipo => Equipo)) =  Exito(f(equipo), tarea)
  def filter(f: (Equipo => Boolean)) = if (f(equipo)) this else Fallo(equipo, tarea)
  def flatMap(f: (Equipo => ResultadoTarea)) = f(equipo)
  def fold[T](e: (Equipo => T))(f: Equipo => T):T = f(equipo)
}

case class Fallo(val equipo:Equipo, val tarea: Tarea) extends ResultadoTarea{
  def map(f: (Equipo => Equipo)) =  this
  def filter(f: (Equipo => Boolean)) = this
  def flatMap(f: (Equipo => ResultadoTarea)) = this
  def fold[T](e: (Equipo => T))(f: (Equipo=>T)):T = e(equipo)
}
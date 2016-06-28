package tadp

import scala.util.Try

class Tarea(consecuencias: Heroe => Heroe, facilidad: (Equipo, Heroe) => Option[Int]) {


  def realizarTarea(equipo: Resultado): Resultado = {
    equipo.flatMap{x => realizarTarea(x)}
  }
  
  def realizarTarea(equipo: Equipo): Resultado = {
      equipo.mejorHeroeSegun { x => facilidad(equipo, x) } match {
        case Some(x) => Exito(equipo.reemplazarMiembro(x, consecuencias(x)))
        case None     => Fallo(equipo, this)

      }
  }
}

trait Resultado {
  def equipo: Equipo
  //def tarea: Tarea
  def map(f: (Equipo => Equipo)): Resultado
  def filter(f: (Equipo => Boolean)): Resultado
  def flatMap(f: (Equipo => Resultado)): Resultado
  def fold[T](e: (Equipo => T))(f: (Equipo => T)): T
  }

case class Exito(val equipo:Equipo) extends Resultado{
  def map(f: (Equipo => Equipo)) =  Exito(f(equipo))
  def filter(f: (Equipo => Boolean)) = if (f(equipo)) this else Error(equipo, "Fallo el filtrado")
  def flatMap(f: (Equipo => Resultado)) = f(equipo)
  def fold[T](e: (Equipo => T))(f: Equipo => T):T = f(equipo)
}

case class Fallo(val equipo:Equipo, val tarea: Tarea) extends Resultado{
  def map(f: (Equipo => Equipo)) =  this
  def filter(f: (Equipo => Boolean)) = this
  def flatMap(f: (Equipo => Resultado)) = this
  def fold[T](e: (Equipo => T))(f: (Equipo=>T)):T = e(equipo)
}

case class Error(val equipo:Equipo, descripcion : String) extends Resultado{
  def map(f: (Equipo => Equipo)) =  this
  def filter(f: (Equipo => Boolean)) = this
  def flatMap(f: (Equipo => Resultado)) = this
  def fold[T](e: (Equipo => T))(f: (Equipo=>T)):T = e(equipo)
}
package tadp

import scala.util.Try

case class Mision(tareas: List[Tarea] = List[Tarea](), recompensa: Try[Equipo] => Try[Equipo]) {
  
  def agregarTarea(tarea:Tarea){
   copy(tareas = tarea::tareas)
  }

  def realizarMision(equipo: Try[Equipo]): Try[Equipo] ={
    for{
      eq <- equipo
      nuevoeq <- realizarMision(eq)
    } yield (nuevoeq)
  }
  
  def realizarMision(equipo: Equipo): Try[Equipo] = {

    recompensa(tareas.foldLeft(Try(equipo)) { (x, y) => y.realizarTarea(x) })

  }

}
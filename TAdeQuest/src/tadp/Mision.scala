package tadp

import scala.util.Try

case class Mision(tareas: List[Tarea] = List[Tarea](), recompensa: Equipo => Equipo) {
  
  def agregarTarea(tarea:Tarea){
   copy(tareas = tarea::tareas)
  }
  
  def realizarMision(equipo: Equipo): Resultado = {
    
    tareas match{
      case x::xs => val semilla=x.realizarTarea(equipo)
      xs.foldLeft(semilla) { (x, y) => y.realizarTarea(x) }.map{x => recompensa(x)}
      case Nil => Exito(recompensa(equipo))

    }
  }
  
}

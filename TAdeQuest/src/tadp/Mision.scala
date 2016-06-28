package tadp

import scala.util.Try

case class Mision(tareas: List[Tarea] = List[Tarea](), recompensa: Equipo => Equipo) {
  
  def agregarTarea(tarea:Tarea){
   copy(tareas = tarea::tareas)
  }

//  def realizarMision(equipo: Try[Equipo]): Try[Equipo] ={
//    for{
//      eq <- equipo
//      nuevoeq <- realizarMision(eq)
//    } yield (nuevoeq)
//  }
  
  def realizarMision(equipo: Equipo): Resultado = {
    
    tareas match{
      case x::xs => val semilla=x.realizarTarea(equipo)
      xs.foldLeft(semilla) { (x, y) => y.realizarTarea(x) }.map{x => recompensa(x)}
      case Nil => Exito(recompensa(equipo), ???)
    }
  }

  
//  def realizarMision(equipo: Equipo): ResultadoTarea = {
//    tareas.foldLeft(Exito(equipo)){(x,y) => for(x1 <- x)yield(y.realizarTarea(x1))}
//  }
//  
//  def resultadoMision(equipo:Equipo): ResultadoTarea ={
//    
//  }
//  
}

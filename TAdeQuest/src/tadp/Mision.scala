package tadp

import scala.util.Try

class Mision (tareas: List[Tarea] = List[Tarea](), recompensa: Try[Equipo] => Try[Equipo]){
  
  def realizarMision(equipo:Equipo): Try[Equipo] = {
    
   
    recompensa(tareas.foldLeft(Try(equipo)){(x, y) => y.realizarTareaRecibeTry(x)})
   
    
  }
  
}
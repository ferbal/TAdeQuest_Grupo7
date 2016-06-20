package tadp

class Mision (tareas: List[Tarea] = List[Tarea](), recompensa: Equipo => Equipo){
  
  def realizarMision(equipo:Equipo): Equipo = {
    
    try{
    recompensa(tareas.foldLeft(equipo){(x, y) => y.realizarTarea(x)})
    }catch{
      case _ => equipo
    }
    
  }
  
}
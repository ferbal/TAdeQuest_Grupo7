package tadp

case class Equipo (heroes: List[Heroe] = List[Heroe]() , oro: Int, nombre:String) {
  
  def mejorHeroeSegun(f:Heroe=> Int): Heroe ={
    heroes.maxBy { x => f(x) }
  }
  
  def obtenerItem(item: Item){
    mejorHeroeSegun({x=> 
      val statnuevo = x.utilizar_item(item.ubicacion, item).get_stat_principal
      statnuevo - x.get_stat_principal})
  }
  
  def obtenerMiembro(heroe:Heroe): Equipo = {
    copy(heroes = heroes.::(heroe))
  }
  
  def reemplazarMiembro(reemplazado:Heroe,reemplazo:Heroe): Equipo = {
    copy(heroes.updated(heroes.indexOf(reemplazado), reemplazo))
  }
  
  def lider: Heroe = {
    mejorHeroeSegun({x=> x.get_stat_principal})
  }
}
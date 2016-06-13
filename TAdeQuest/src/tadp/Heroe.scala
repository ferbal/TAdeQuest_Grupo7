package tadp

class Heroe (hp: Int = 100, fuerza: Int = 20, velocidad: Int = 45, inteligencia: Int = 5){
  var inventario: Inventario = {
    val inv = new Inventario
    inv
  }
  
  //var a : Map[String,Option[Item]] = ("cabeza" -> Option(new Casco))
  
  var stats: Stats = {
    val st = new Stats(hp,fuerza,velocidad,inteligencia)
    st
  }
  var trabajo: Trabajo = new Trabajo("Guerrero")
  
  def get_hp_actual():Int = {
    this.stats.hp + this.trabajo.stats.hp;    
  }
  
}


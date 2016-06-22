package tadp

case class Stats(hp: Int = 0, fuerza: Int = 0 , velocidad: Int = 0, inteligencia: Int = 0){
  
  def incrementar (stat_to_add: Stats): Stats ={
    copy(hp = hp + stat_to_add.hp
    ,fuerza = fuerza + stat_to_add.fuerza
    ,velocidad = velocidad + stat_to_add.velocidad
    ,inteligencia = inteligencia + stat_to_add.inteligencia)    
  }
  
    def incrementar (Hp:Int, Fuerza:Int, Velocidad:Int, Inteligencia:Int): Stats ={
    copy(hp = hp + Hp
    ,fuerza = fuerza + Fuerza
    ,velocidad = velocidad + Velocidad
    ,inteligencia = inteligencia + Inteligencia)    
  }
  
}
package tadp

class Stats(_hp: Int = 0, _fuerza: Int = 0 , _velocidad: Int = 0, _inteligencia: Int = 0){
  var hp: Integer = _hp 
  var fuerza: Integer = _fuerza
  var velocidad: Integer = _velocidad
  var inteligencia: Integer = _inteligencia
  
  def incrementar (stat_to_add: Stats){
    this.hp += stat_to_add.hp
    this.fuerza += stat_to_add.fuerza
    this.velocidad += stat_to_add.velocidad
    this.inteligencia += stat_to_add.inteligencia    
  }
  
}
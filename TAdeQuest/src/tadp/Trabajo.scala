package tadp

class Trabajo (trabajo:String){
  var stats: Stats = _
  var stat_principal: String = _
  
  trabajo match {
    case "Guerrero" => guerrero
    case "Mago" => mago
    case "Ladron" => ladron
  }
  
  def guerrero{    
    //Stats(HP,Fuerza,Velocidad,Inteligencia)
    this.stats = new Stats(10,15,0,-10)
    this.stat_principal = "Fuerza"
  }
  
  def mago{
    //Stats(HP,Fuerza,Velocidad,Inteligencia)
    this.stats = new Stats(0,-20,0,20)
    this.stat_principal = "Inteligencia"
  }
  
  def ladron{    
    //Stats(HP,Fuerza,Velocidad,Inteligencia)
    this.stats = new Stats(-5,0,10,0)
    this.stat_principal = "Velocidad"
  }
}
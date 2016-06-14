package tadp

class Trabajo (trabajo_asignado:String){
  var stats: Stats = _
  var stat_principal: String = _
  var tipo: String = _
  
  trabajo_asignado match {
    case Habilidad.GUERRERO => guerrero
    case Habilidad.MAGO => mago
    case Habilidad.LADRON => ladron
    case _ => sin_trabajo
  }
  
  def guerrero{    
    //Stats(HP,Fuerza,Velocidad,Inteligencia)
    this.stats = new Stats(10,15,0,-10)
    this.stat_principal = "Fuerza"
    this.tipo = Habilidad.GUERRERO
  }
  
  def sin_trabajo{
    this.stats = new Stats(0,0,0,0)
    this.stat_principal = "Sin Trabajo"
    this.tipo = Habilidad.SIN_TRABAJO
  }
  
  def mago{
    //Stats(HP,Fuerza,Velocidad,Inteligencia)
    this.stats = new Stats(0,-20,0,20)
    this.stat_principal = "Inteligencia"
    this.tipo = Habilidad.MAGO
  }
  
  def ladron{    
    //Stats(HP,Fuerza,Velocidad,Inteligencia)
    this.stats = new Stats(-5,0,10,0)
    this.stat_principal = "Velocidad"
    this.tipo = Habilidad.LADRON
  }
}
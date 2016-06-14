package tadp
//CASCOS
class Casco () extends Item{
   ubicacion = Posicion.CABEZA   
   def beneficios(unHeroe : Heroe): Stats = {     
     var st = new Stats (unHeroe.stats.hp + 15,0,0,0)     
     st
   }
   
   def puede_usar(unHeroe : Heroe) : Boolean = {
     true
   }
}

class Casco_Supremo () extends Item {  
  ubicacion = Posicion.CABEZA  
  def beneficios(unHeroe : Heroe): Stats = {      
      var st = new Stats (unHeroe.stats.hp + 100,0,0,0)     
     st
  }
   
  def puede_usar(unHeroe : Heroe) : Boolean = {
     true
  }
}

class Espada_de_la_vida extends Item{
  ubicacion = "Mano Derecha";  
  //Hace que la fuerza del Heroe sea igual a la de su HP
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (-1,0,0,0)     
     st
   }
  
  def puede_usar(unHeroe : Heroe) : Boolean = {
     true
  }
}

class Espada_Doble extends Item{
  ubicacion = Posicion.AMBAS_MANOS;
  //Hace que la fuerza del Heroe sea igual a la de su HP
  def beneficios(unHero: Heroe): Stats = {
    
     var st = new Stats (50,0,0,0)     
     st
   }
  def puede_usar(unHeroe : Heroe) : Boolean = {
     true
  }
}

class Espada_Zurda extends Item{
  ubicacion = Posicion.MANO_IZQ;
  //Hace que la fuerza del Heroe sea igual a la de su HP
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (200,0,0,0)     
     st
   }
  def puede_usar(unHeroe : Heroe) : Boolean = {
     true
  }
}

class Casco_Vikingo (unHeroe : Heroe) extends Item {  
  ubicacion = Posicion.CABEZA  
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (10,0,0,0)     
     st
  }  
  def puede_usar(unHeroe : Heroe) : Boolean = {
     unHeroe.stats.fuerza > 30
  }
}
//Stats(HP,Fuerza,Velocidad,Inteligencia)
class Palito_Magico () extends Item {  
  ubicacion = Posicion.MANO_DER  
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (0,0,0,20)     
     st
  }  
  def puede_usar(unHeroe : Heroe) : Boolean = {
     unHeroe.trabajo.tipo == Habilidad.MAGO || (unHeroe.trabajo.tipo == Habilidad.LADRON && unHeroe.stats.inteligencia > 30) 
  }
}

//Stats(HP,Fuerza,Velocidad,Inteligencia)
class Armadura_Elegante_Sport () extends Item {  
  ubicacion = Posicion.TORSO 
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (-30,0,30,0)     
     st
  }  
  def puede_usar(unHeroe : Heroe) : Boolean = {
     true 
  }
}

//Stats(HP,Fuerza,Velocidad,Inteligencia)
class Arco_Viejo () extends Item {  
  ubicacion = Posicion.AMBAS_MANOS  
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (0,2,0,0)     
     st
  }  
  def puede_usar(unHeroe : Heroe) : Boolean = {
     true 
  }
}

//Stats(HP,Fuerza,Velocidad,Inteligencia)
class Escudo_Anti_Robo () extends Item {  
  ubicacion = Posicion.MANO_IZQ  
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (20,0,0,0)     
     st
  }  
  def puede_usar(unHeroe : Heroe) : Boolean = {
     unHeroe.trabajo.tipo != Habilidad.LADRON && unHeroe.stats.fuerza > 20 
  }
}



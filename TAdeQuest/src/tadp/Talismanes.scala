package tadp

class Talisman extends Item {
  ubicacion = "Mano Derecha";
  //Hace que la fuerza del Heroe sea igual a la de su HP
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (10,0,0,0)     
     st
   }
  def puede_usar(unHeroe : Heroe) : Boolean = {
    true
  }
}

//Stats(HP,Fuerza,Velocidad,Inteligencia)
class Talisman_Dedicacion () extends Item {  
  ubicacion = Posicion.TALISMANES  
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (0,0,0,0) //Aumenta 10 % todos los stats (del valor principal del Stat de trabajo)
     st
  }  
  def puede_usar(unHeroe : Heroe) : Boolean = {
    true
  }
}

//Stats(HP,Fuerza,Velocidad,Inteligencia)
class Talisman_Minimalismo () extends Item {  
  ubicacion = Posicion.TALISMANES  
  def beneficios(unHeroe : Heroe): Stats = {
     var st = new Stats (50,0,0,0) //-10 hp por cada otro item equipado     
     st
  }  
  def puede_usar(unHeroe : Heroe) : Boolean = {
    true
  }
}

//Stats(HP,Fuerza,Velocidad,Inteligencia)
class Vincha_Bufalo_Agua () extends Item {  
  
  ubicacion = Posicion.CABEZA  
  
  def beneficios(unHeroe : Heroe): Stats = {     
      var st = new Stats 
      if (unHeroe.stats.fuerza > unHeroe.stats.inteligencia){
        st.inteligencia +=30        
      }else {
        st.fuerza += 10
        st.hp +=10
        st.velocidad +=10 
      }        
     st
  }  
  
  def puede_usar(unHeroe : Heroe) : Boolean = {
    unHeroe.trabajo.stat_principal == "Sin Trabajo"
  }
}

class Talisman_Maldito () extends Item {  
  
  ubicacion = Posicion.CABEZA  
  
  def beneficios(unHeroe : Heroe): Stats = {     
      var st = new Stats (1,1,1,1)            
     st
  }  
  
  def puede_usar(unHeroe : Heroe) : Boolean = {
    true
  }
}
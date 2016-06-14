package tadp

class Heroe (hp: Int = 100, fuerza: Int = 20, velocidad: Int = 45, inteligencia: Int = 5){
   
  var inventario : Map[String,Item] = Map(  Posicion.CABEZA -> new Casco,
                                        Posicion.MANO_DER-> new Espada_de_la_vida                                                
                                     )
  
  var stats: Stats = {
    val st = new Stats(hp,fuerza,velocidad,inteligencia)
    st
  }
  var trabajo: Trabajo = new Trabajo("Guerrero")
  
  def get_hp_actual():Int = {    
    val st : Stats = new Stats
    inventario map { case (ubi, it) =>  st.incrementar(it.beneficios())} //Funciona sin OPTION
    
    /*for{
      (ubi,it) <- prueba
      val item : Item= it.get if !it.isEmpty
      st.incrementar(item.beneficios()) 
    } yield (st)*/
    
    this.stats.hp + this.trabajo.stats.hp + st.hp;    
  }
  
}


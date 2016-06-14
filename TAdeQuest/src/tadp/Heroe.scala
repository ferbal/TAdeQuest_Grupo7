package tadp

import com.sun.beans.decoder.TrueElementHandler
import scala.collection.mutable.Map

class Heroe (hp: Int = 100, fuerza: Int = 20, velocidad: Int = 45, inteligencia: Int = 5){
   
  var inventario : Map[String,Item] = Map(  Posicion.CABEZA -> new Casco,
                                        Posicion.MANO_DER-> new Espada_de_la_vida                                                
                                     )
  
  var stats: Stats = {
    val st = new Stats(hp,fuerza,velocidad,inteligencia)
    st
  }
  var trabajo: Trabajo = new Trabajo(Habilidad.SIN_TRABAJO)
  
  def definir_trabajo (habilidad : String){
    this.trabajo = new Trabajo(habilidad)
  }
  
  def get_hp_actual():Int = {    
    val st : Stats = new Stats
    inventario map { case (ubi, it) =>  st.incrementar(it.beneficios(this))} //Funciona sin OPTION
    
    /*for{
      (ubi,it) <- prueba
      val item : Item= it.get if !it.isEmpty
      st.incrementar(item.beneficios()) 
    } yield (st)*/
    
    this.stats.hp + this.trabajo.stats.hp + st.hp;    
  }
  
  def utilizar_item (ubicacion: String,item : Item) {
    //Filtro inventario: me quedo con todos los items menos el que voy a usar
    inventario = for {
                        (u,i) <- inventario if !validar_ubicacion(u, item)    
                      } yield (u,i)
    inventario.update(ubicacion, item)
  }
  
  def validar_ubicacion (ubicacion_actual : String , item_nuevo : Item) : Boolean = {
    if (item_nuevo.ubicacion == Posicion.AMBAS_MANOS && (ubicacion_actual == Posicion.MANO_DER || ubicacion_actual == Posicion.MANO_IZQ)){
      return true
    }else{
      return item_nuevo.ubicacion == ubicacion_actual
    }    
  }
  
}


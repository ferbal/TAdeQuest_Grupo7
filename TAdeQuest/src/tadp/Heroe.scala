package tadp

import com.sun.beans.decoder.TrueElementHandler
import scala.collection.mutable.Map
import java.util.HashMap

case class Heroe (stats: Stats = new Stats(100,20,45,5),
    inventario: Map[String,Item] = Map[String,Item](), talismanes: List[Item] = List[Item](),
    trabajo: Trabajo = new Trabajo(Habilidad.SIN_TRABAJO)){   
  
  def definir_trabajo (habilidad : String): Heroe ={
    copy(trabajo= new Trabajo(habilidad))
  }
  
  def get_stats_actuales():Stats = {    
    var st : Stats = this.stats

    st.incrementar(this.trabajo.stats)
    
    val itemsTotales = inventario.values ++ talismanes
    
    itemsTotales.foldLeft(st) {(stats, item) => item.beneficios(stats, this)}
    
    return st    
  }
  
  def get_stat_principal(): Int = {
    val st = get_stats_actuales()
    trabajo.stat_principal match{
      case "Fuerza" => st.fuerza
      case "Inteligencia" => st.inteligencia
      case "Velocidad" => st.velocidad
      case "HP" => st.hp
      case _ => 0
    }
  }
  
  def utilizar_item (ubicacion: String,item : Item): Heroe = {
    //Filtro inventario: me quedo con todos los items menos el que voy a usar
    var invMutable = inventario 
    invMutable = for {
                        (u,i) <- inventario if !validar_ubicacion(u, item)    
                      } yield (u,i)
    invMutable.update(ubicacion, item)
    copy(inventario = invMutable)
  }
  
  def validar_ubicacion (ubicacion_actual : String , item_nuevo : Item) : Boolean = {
    if (item_nuevo.ubicacion == Posicion.AMBAS_MANOS && (ubicacion_actual == Posicion.MANO_DER || ubicacion_actual == Posicion.MANO_IZQ)){
      return true
    }else{
      return item_nuevo.ubicacion == ubicacion_actual
    }    
  }
  
}


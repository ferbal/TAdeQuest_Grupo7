package tadp

import com.sun.beans.decoder.TrueElementHandler
import scala.collection.mutable.Map
import java.util.HashMap
import scala.collection.mutable.MutableList

case class Heroe (stats: Stats = Stats(100,20,45,5),
    inventario: Map[Posicion,Item] = Map[Posicion,Item](), 
    talismanes: List[Item] = List[Item](),
    trabajo: Option[Trabajo] = None){   
  
  
  def cambiarTrabajoA (nuevoTrabajo : Option[Trabajo]): Heroe ={
     copy(trabajo = nuevoTrabajo)
  }
  
  def get_stats_actuales():Stats = {    
    var st : Stats = stats //this.stats

    if (trabajo != None)
      st= trabajo.get.aplicarModificadorDeStats(st,this)  
    
    val itemsTotales = inventario.values ++ talismanes
    
    itemsTotales.foldLeft(st) {(stats, item) => item.beneficios(stats, this)}

  }
  
  def get_stat_principal(): Int = {
    val st = get_stats_actuales()
    trabajo match {      
      case None => throw new Exception("No tiene trabajo asignado")
      case unTrabajo => unTrabajo.get.stat_principal match{
                        case Fuerza => st.fuerza
                        case Inteligencia => st.inteligencia
                        case Velocidad => st.velocidad
                        case Hp => st.hp
                        case _ => throw new Exception("No tiene Stat Principal")
                      } 
    }
    
  }
  
  def utilizar_item (ubicacion: Posicion,item : Item): Heroe = {
    //Filtro inventario: me quedo con todos los items menos el que voy a usar
    ubicacion match{
      
    case Talismanes => {
      var talismanesMutables = talismanes
      talismanesMutables = item :: talismanes
      copy(talismanes = talismanesMutables)}
      
    case _ => {
      var invMutable = inventario 
      invMutable = for {
                        (u,i) <- inventario if !validar_ubicacion(u, item)    
                      } yield (u,i)
      invMutable.update(ubicacion, item)
      copy(inventario = invMutable)}
    }  
  }
  
  def validar_ubicacion (ubicacion_actual : Posicion , item_nuevo : Item) : Boolean = {
    item_nuevo.ubicacion match {
      case AmbasManos => verificoUbicacionActualDeMano(ubicacion_actual)
      case ManoDer => verificoUbicacionActualDeMano(ubicacion_actual)
      case ManoIzq => verificoUbicacionActualDeMano(ubicacion_actual)
      case otra => otra == item_nuevo.ubicacion
      }
    }

 
  def verificoUbicacionActualDeMano (ubicacion_actual : Posicion) : Boolean = {
    ubicacion_actual match {
        case AmbasManos => true
        case ManoDer => true
        case ManoIzq => true
        case otra => false
    }
  }
  
  def getTipoTrabajo (): Habilidad = { 
    trabajo match {
      case None => throw new Exception("No tiene trabajo asignado")
      case unTrabajo => unTrabajo.get.tipo
    }    
  }
}



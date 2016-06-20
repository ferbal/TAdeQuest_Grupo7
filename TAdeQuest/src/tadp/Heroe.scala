package tadp

import com.sun.beans.decoder.TrueElementHandler
import scala.collection.mutable.Map
import java.util.HashMap
import scala.collection.mutable.MutableList

case class Heroe (stats: Stats = new Stats(100,20,45,5),
    inventario: Map[Posicion,Item] = Map[Posicion,Item](), 
    talismanes: MutableList[Item] = MutableList[Item](),
    trabajo: Option[Trabajo] = None){//new Trabajo(Habilidad.SIN_TRABAJO, Stat_Principal.NINGUNO, {(st,x)=> st})){   
  
  
  def cambiarTrabajoA (nuevoTrabajo : Option[Trabajo]): Heroe ={
     copy(trabajo = nuevoTrabajo)
  }
  
  def get_stats_actuales():Stats = {    
    var st : Stats = new Stats//this.stats

    if (trabajo != None)
      st= trabajo.get.aplicarModificadorDeStats(st,this)  
    
    val itemsTotales = inventario.values ++ talismanes
    
    itemsTotales.foldLeft(st) {(stats, item) => item.beneficios(stats, this)}
    
    return st    
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
      talismanesMutables += item
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
    if (item_nuevo.ubicacion == AmbasManos && (ubicacion_actual == ManoDer|| ubicacion_actual == ManoIzq)){
      return true
    }else{
      return item_nuevo.ubicacion == ubicacion_actual
    }    
  }
  
  def getTipoTrabajo (): Habilidad = { 
    trabajo match {
      case None => throw new Exception("No tiene trabajo asignado")
      case unTrabajo => unTrabajo.get.tipo
    }    
  }
}


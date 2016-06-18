package tadp

import com.sun.beans.decoder.TrueElementHandler
import scala.collection.mutable.Map
import java.util.HashMap
import scala.collection.mutable.MutableList

class Heroe (hp: Int = 100, fuerza: Int = 20, velocidad: Int = 45, inteligencia: Int = 5){
   
  var inventario = Map[String,Item]()
  
  var talismanes = MutableList[Item]()
  
  var stats: Stats = {
    val st = new Stats(hp,fuerza,velocidad,inteligencia)
    st
  }
  var trabajo: Trabajo = new Trabajo(Habilidad.SIN_TRABAJO, Stat_Principal.NINGUNO, {(st,x)=> st})
  
  def cambiarTrabajoA (nuevoTrabajo : Trabajo){
    this.trabajo = nuevoTrabajo
  }
  
  def get_stats_actuales():Stats = {    
    var st : Stats = new Stats//this.stats
    
    /*
    inventario map { 
        case (ubi, it) =>  
            st.incrementar(it.beneficios(this))
           } 
    for {
          (u,i) <- inventario 
                                           
        } yield (st.incrementar(i.beneficios(this)))
    */        
    st= trabajo.aplicarModificadorDeStats(st,this)
    
    st.incrementar(this.stats)
    
    val itemsTotales = inventario.values ++ talismanes
    
    itemsTotales.foldLeft(st) {(stats, item) => item.beneficios(stats, this)}
    
    return st    
  }
  
  def utilizar_item (ubicacion: String,item : Item) {
    if (ubicacion == Posicion.TALISMANES){        
        talismanes += item
    }else{
          //Filtro inventario: me quedo con todos los items menos el que voy a usar
          inventario = for {
                              (u,i) <- inventario if !validar_ubicacion(u, item)    
                            } yield (u,i)
          inventario.update(ubicacion, item)
    }
  }
  
  def dejar_de_usar_item (ubicacion : String , item :Item) {
   /* 
    * if (ubicacion == Posicion.TALISMANES){        
        talismanes = for {
            t <- talismanes if (t.nombre == item.nombre)             
        } yield t
    }else{          
          inventario = for {
                              (u,i) <- inventario if !validar_ubicacion(u, item)    
                            } yield (u,i)          
    }
    * 
    */
  }
  
  def validar_ubicacion (ubicacion_actual : String , item_nuevo : Item) : Boolean = {
    if (item_nuevo.ubicacion == Posicion.AMBAS_MANOS && (ubicacion_actual == Posicion.MANO_DER || ubicacion_actual == Posicion.MANO_IZQ)){
      return true
    }else{
      return item_nuevo.ubicacion == ubicacion_actual
    }    
  }
  
}


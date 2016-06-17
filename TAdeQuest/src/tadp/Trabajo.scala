package tadp

class Trabajo (nombre:String, statPrincipal: String,  modificadorDeStats: (Stats,Heroe) => Stats){

  val stat_principal = statPrincipal
  val tipo = nombre

  
  
  def aplicarModificadorDeStats (st:Stats,unHeroe : Heroe) : Stats={    
   return modificadorDeStats(st, unHeroe)
  }
}
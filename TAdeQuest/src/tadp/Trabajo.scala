package tadp

case class Trabajo (tipo: Habilidad, stat_principal: StatPrincipal,  modificadorDeStats: (Stats,Heroe) => Stats){

  //val stat_principal = statPrincipal
  //val tipo = nombre
  
  def aplicarModificadorDeStats (st:Stats,unHeroe : Heroe) : Stats={    
   return modificadorDeStats(st, unHeroe)
  }
}
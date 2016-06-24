package tadp

import tadp.Guerrero

class Trabajo (_tipo: Habilidad, _stat_principal: StatPrincipal,  modificadorDeStats: (Stats,Heroe) => Stats){
 val tipo : Habilidad = _tipo
 val stat_principal : StatPrincipal = _stat_principal
 
  def aplicarModificadorDeStats (st:Stats,unHeroe : Heroe) : Stats={    
   return modificadorDeStats(st, unHeroe)
  }
}

case class Guerrero_Job ( _tipo: Habilidad = Guerrero, 
                          _stat_principal: StatPrincipal = Fuerza,  
                          modificadorDeStats: (Stats,Heroe) => Stats = (unStat,unHeroe)=> unStat.incrementar(new Stats(10,15,0,-10))) 
          extends Trabajo(_tipo,_stat_principal,modificadorDeStats)

case class Mago_Job ( _tipo: Habilidad = Mago, 
                          _stat_principal: StatPrincipal = Inteligencia,  
                          modificadorDeStats: (Stats,Heroe) => Stats = (unStat,unHeroe)=> unStat.incrementar(new Stats(0,-20,0,20))) 
          extends Trabajo(_tipo,_stat_principal,modificadorDeStats)

case class Ladron_Job ( _tipo: Habilidad = Ladron, 
                          _stat_principal: StatPrincipal = Velocidad,  
                          modificadorDeStats: (Stats,Heroe) => Stats = (unStat,unHeroe)=> unStat.incrementar(new Stats(-5,0,10,0))) 
          extends Trabajo(_tipo,_stat_principal,modificadorDeStats)

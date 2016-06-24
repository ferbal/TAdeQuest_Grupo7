package tadp

import tadp.Item

case class Palito_Magico (unaUbicacion : Posicion = ManoDer, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unStat.incrementar(new Stats(0, 0, 0, 20)), 
                          condicion : Heroe => Boolean = unHeroe =>
                                                          unHeroe.getTipoTrabajo() == Mago || (unHeroe.getTipoTrabajo()  == Ladron &&
                                                          unHeroe.stats.inteligencia > 30)                           
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Casco_Vikingo (unaUbicacion : Posicion = Cabeza, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unStat.incrementar(new Stats(10,0,0,0)), 
                          condicion : Heroe => Boolean = unHeroe => unHeroe.stats.fuerza > 30                           
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Armadura_Elegante_Sport (unaUbicacion : Posicion = Torso, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unStat.incrementar(new Stats(-30,0,30,0)), 
                          condicion : Heroe => Boolean = unHeroe => true                            
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Arco_Viejo (unaUbicacion : Posicion = AmbasManos, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unStat.incrementar(new Stats(0,3,0,0)), 
                          condicion : Heroe => Boolean = unHeroe => true                            
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Escudo_Anti_Robo (unaUbicacion : Posicion = ManoDer, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unStat.incrementar(new Stats(20,0,0,0)), 
                          condicion : Heroe => Boolean = unHeroe => unHeroe.getTipoTrabajo() != Ladron && unHeroe.stats.fuerza > 20                           
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Vincha_Bufalo_Agua (unaUbicacion : Posicion = Cabeza, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) =>
                                                            if (unHeroe.stats.fuerza > unHeroe.stats.inteligencia) {
                                                              unStat.incrementar(0,0,0,30)
                                                            } else {
                                                              unStat.incrementar(10,10,10,0)
                                                            }, 
                          condicion : Heroe => Boolean = unHeroe => unHeroe.trabajo == None                            
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Espada_De_La_Vida (unaUbicacion : Posicion = ManoIzq, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => new Stats(unStat.hp,unStat.hp,unStat.velocidad,unStat.inteligencia), 
                          condicion : Heroe => Boolean =  unHeroe => true
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Casco_Supremo (unaUbicacion : Posicion = Cabeza, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unStat.incrementar(new Stats (unHeroe.stats.hp + 100,0,0,0)), 
                          condicion : Heroe => Boolean =  unHeroe => true
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Espada_Doble (unaUbicacion : Posicion = AmbasManos, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unStat.incrementar(new Stats (50,0,0,0)), 
                          condicion : Heroe => Boolean =  unHeroe => true
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Espada_Zurda (unaUbicacion : Posicion = ManoIzq, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unStat.incrementar(new Stats (200,0,0,0)), 
                          condicion : Heroe => Boolean =  unHeroe => true
                          ) extends Item(unaUbicacion,efecto,condicion)

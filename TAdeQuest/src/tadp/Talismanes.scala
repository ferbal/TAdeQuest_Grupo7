package tadp

case class Talisman_De_Dedicacion (unaUbicacion : Posicion = Talismanes, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unHeroe.aplicarFuncionStatPrincipal(unHeroe, i => i * 10 / 100), 
                          condicion : Heroe => Boolean = unHeroe => unHeroe.getTipoTrabajo() != Ladron && unHeroe.stats.fuerza > 20                           
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Talisman_del_Minimalismo (unaUbicacion : Posicion = Talismanes, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => new Stats(50 - unHeroe.cantidadItemsEquipados()*10,0,0,0), 
                          condicion : Heroe => Boolean = unHeroe => true                          
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Talisman_Maldito (unaUbicacion : Posicion = Talismanes, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => new Stats(1,1,1,1), 
                          condicion : Heroe => Boolean = unHeroe => true                          
                          ) extends Item(unaUbicacion,efecto,condicion)

case class Talisman_Comun (unaUbicacion : Posicion = Talismanes, 
                          efecto : (Stats,Heroe) => Stats = (unStat,unHeroe) => unStat.incrementar(new Stats(10,0,0,0)), 
                          condicion : Heroe => Boolean = unHeroe => true                          
                          ) extends Item(unaUbicacion,efecto,condicion)
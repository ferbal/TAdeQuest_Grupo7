package tadp

class Item (unaUbicacion: Posicion, efecto: (Stats, Heroe) => Stats, condicion: Heroe=>Boolean) {
   var ubicacion: Posicion = unaUbicacion   
   //var nombre: String = _
   
   def beneficios (unosStats: Stats, unHeroe : Heroe) : Stats ={
     efecto(unosStats, unHeroe)
   }
   def puede_usar(unHeroe : Heroe) : Boolean ={
     condicion(unHeroe)
   }

}


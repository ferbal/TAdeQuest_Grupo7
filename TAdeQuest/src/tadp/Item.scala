package tadp

class Item (ubicacion: String, efecto: (Stats, Heroe) => Stats, condicion: Heroe=>Boolean) {
   var ubicacion: String = _   
   //var nombre: String = _
   
   def beneficios (unosStats: Stats, unHeroe : Heroe) : Stats ={
     efecto(unosStats, unHeroe)
   }
   def puede_usar(unHeroe : Heroe) : Boolean ={
     condicion(unHeroe)
   }
  
}


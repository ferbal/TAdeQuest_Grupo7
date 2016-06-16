package tadp

class Item (ubicacion: String, efecto: Heroe => Stats, condicion: Heroe=>Boolean) {
   var ubicacion: String = _   
   //ver funcion CAMBIO
   
   def beneficios (unHeroe : Heroe) : Stats ={
     efecto(unHeroe)
   }
   def puede_usar(unHeroe : Heroe) : Boolean ={
     condicion(unHeroe)
   }
}


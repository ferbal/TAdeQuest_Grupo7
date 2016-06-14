package tadp

abstract class Item{
   var ubicacion: String = _   
   //ver funcion CAMBIO
   
   def beneficios (unHeroe : Heroe) : Stats
   def puede_usar(unHeroe : Heroe) : Boolean
}


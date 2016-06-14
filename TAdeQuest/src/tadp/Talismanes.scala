package tadp

class Talisman extends Item {
  ubicacion = "Mano Derecha";
  //Hace que la fuerza del Heroe sea igual a la de su HP
  def beneficios(): Stats = {
     var st = new Stats (10,0,0,0)     
     st
   }
}
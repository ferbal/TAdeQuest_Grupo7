package tadp

class Casco () extends Item{
   
   ubicacion = "Cabeza"   
   
   def beneficios(sts : Stats ): Stats = {
     var st = new Stats 
     st.hp  = sts.hp + 10     
     st
   }
}


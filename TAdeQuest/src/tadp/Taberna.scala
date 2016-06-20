package tadp

class Taberna {
  
 var misiones: List[Mision] = List[Mision]()
 
 def agregarMision(mision:Mision)={
   mision::misiones
 }
 def mejorMisionSegunCriterio(equipo:Equipo, criterio:(Equipo,Equipo) => Boolean, mision1:Mision, mision2:Mision):Mision={
   if (criterio(mision1.realizarMision(equipo),mision2.realizarMision(equipo))
       {return mision1}
   else
       {return mision2}
 }
 
 def elegirMision(equipo: Equipo, criterio: (Equipo,Equipo) => Boolean): Mision ={
   
   val comparador = mejorMisionSegunCriterio equipo criterio _ _ 
   val resultado = misiones.reduceLeft()
   

   
 }
  
}
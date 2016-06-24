package tadp

class Tarea(consecuencias: Heroe => Heroe, facilidad: (Equipo, Heroe) => Int) {

  def realizarTarea(equipo: Equipo): Equipo = {

    equipo.mejorHeroeSegun { x => facilidad(equipo, x) } match {
      case x :: xs => equipo.reemplazarMiembro(x, consecuencias(x))
      case Nil     => throw new Exception("No hay ningun heroe capaz de realizar la tarea", this.toString())
    }
  }
}
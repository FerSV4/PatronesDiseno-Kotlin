package com.example.componentesapp.patrones

class GestorTareas {
    private val observadores = mutableListOf<ObservadorTareas>()
    private val tareas = mutableListOf<String>()

    fun NewObservador(obs: ObservadorTareas) = observadores.add(obs)
    fun DeleteObservador(obs: ObservadorTareas) = observadores.remove(obs)

    fun agregarTarea(nombre: String) {
        tareas.add(nombre)
        notificarObservadores(nombre)
    }

    private fun notificarObservadores(tarea: String) {
        observadores.forEach { it.actualizar(tarea) }
    }
}


interface ObservadorTareas {
    fun actualizar(tarea: String)
}


class NotificadorTareas : ObservadorTareas {
    override fun actualizar(tarea: String) {
        println("Nueva tarea : $tarea")
    }
}

fun main() {
    val gestor = GestorTareas()
    val notificador = NotificadorTareas()
    gestor.NewObservador(notificador)

    gestor.agregarTarea("Practicar para el examen de cálculo")
    gestor.agregarTarea("Entregar tarea de App Movil")
}

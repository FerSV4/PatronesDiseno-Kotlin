package com.example.componentesapp.patrones

import java.util.*


interface Prototipo<T> {
    fun clone(): T
}
var intid : Int = 100

data class TareaPrototype(
    val id: String,
    val titulo: String,
    val descripcion: String?,
    val fechaEntrega: Long,
    val materia: String?,
    val prioridad: String = "NORMAL"
) : Prototipo<TareaPrototype> {

    override fun clone(): TareaPrototype {
	var id : Int = intid++
        return this.copy(id.toString())
    }
}


fun main() {
    val plantilla = TareaPrototype(
        id = intid++.toString(),
        titulo = "Tarea semanal base",
        descripcion = "Actividad de práctica semanal",
        fechaEntrega = System.currentTimeMillis() + (3 * 24 * 60 * 60 * 1000), 
        materia = "Programación",
        prioridad = "NORMAL"
    )

    val tarea1 = plantilla.clone().copy(titulo = "Tarea semanal 1")
    val tarea2 = plantilla.clone().copy(titulo = "Tarea semanal 2")

    println("Plantilla: $plantilla")
    println("Tarea 1 clonada: $tarea1")
    println("Tarea 2 clonada: $tarea2")
}
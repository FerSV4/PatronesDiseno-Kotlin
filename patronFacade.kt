package com.example.componentesapp.patrones

import java.util.*

data class Tarea(
    val id: String,
    val titulo: String,
    val descripcion: String?,
    val fechaEntrega: Long,
    val materia: String?,
    val prioridad: Prioridad = Prioridad.NORMAL
)

enum class Prioridad { BAJA, NORMAL, ALTA, CRITICA }

interface AlmacenTareas {
    fun guardar(tarea: Tarea)
    fun eliminar(id: String)
    fun obtenerTodas(): List<Tarea>
    fun buscarPorId(id: String): Tarea?
}

class ListaMemoria : AlmacenTareas {
    private val almacenamiento = mutableMapOf<String, Tarea>()

    override fun guardar(tarea: Tarea) {
        almacenamiento[tarea.id] = tarea
    }

    override fun eliminar(id: String) {
        almacenamiento.remove(id)
    }

    override fun obtenerTodas(): List<Tarea> = almacenamiento.values.toList()

    override fun buscarPorId(id: String): Tarea? = almacenamiento[id]
}

class ProgramadorRecordatorios {
    private val programados = mutableMapOf<String, Long>()

    fun programar(tarea: Tarea) {
        programados[tarea.id] = tarea.fechaEntrega
        println("Tarea programada: '${tarea.titulo}' el ${Date(tarea.fechaEntrega)}")
    }

    fun cancelar(id: String) {
        programados.remove(id)
        println("Tarea cancelada, ID: $id")
    }
}

class Notificador {
    fun mostrarNotificacion(tarea: Tarea) {
        println("(Notificación): ${tarea.titulo} (${tarea.materia})")
    }
}

// PATRON FACADE
class GestorTareasFacade(
    private val AlmacenDatos: AlmacenTareas,
    private val programador: ProgramadorRecordatorios,
    private val notificador: Notificador
) {
    fun agregarTarea(tarea: Tarea) {
        AlmacenDatos.guardar(tarea)
        programador.programar(tarea)
    }

    fun eliminarTarea(id: String) {
        AlmacenDatos.buscarPorId(id)?.let {
            programador.cancelar(id)
            AlmacenDatos.eliminar(id)
        }
    }

    fun notificarAhora(id: String) {
        AlmacenDatos.buscarPorId(id)?.let { notificador.mostrarNotificacion(it) }
    }

    fun listarTareas(): List<Tarea> = AlmacenDatos.obtenerTodas()
}

fun main() {
    val AlmacenDatos = ListaMemoria()
    val programador = ProgramadorRecordatorios()
    val notificador = Notificador()

    val gestor = GestorTareasFacade(AlmacenDatos, programador, notificador)

    val tarea = Tarea(
        id = "1",
        titulo = "Examen de Aplicaciones Moviles",
        descripcion = "Kotlin desde lo basico hasta POO",
        fechaEntrega = System.currentTimeMillis(),
        materia = "Aplicaciones Moviles",
        prioridad = Prioridad.ALTA
    )

    gestor.agregarTarea(tarea)
    gestor.notificarAhora("1")
    println("Tareas registradas: ${gestor.listarTareas()}")
    gestor.eliminarTarea("1")
}

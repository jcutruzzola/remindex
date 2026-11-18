package com.example.remindex
import android.app.Person
import java.time.LocalDateTime

// Reminder Categories
sealed class Categoria(val etiqueta: String) {
    object Personal: Categoria("Personal")
    object Trabajo: Categoria("Trabajo")
    object Salud: Categoria("Salud")
    object Estudios: Categoria("Estudios")

    companion object {
        val options: List<Categoria> by lazy {
            listOf(
                Personal,
                Trabajo,
                Salud,
                Estudios
            )
        }
    }
}
// Reminder Priorities
sealed class Prioridad(val etiqueta: String, val nivel: Int) {

    object Baja: Prioridad("Baja", 1)
    object Media: Prioridad("Media", 2)
    object Alta: Prioridad("Alta", 3 )


    companion object {
        val options: List<Prioridad> by lazy {
            listOf(
                Baja,
                Media,
                Alta
            )
        }
    }
}
// Reminder States
sealed class EstadoRecordatorio(val etiqueta: String) {
    object Pendiente: EstadoRecordatorio("Pendiente")
    object EnCurso: EstadoRecordatorio("En curso")
    object Completado: EstadoRecordatorio("Completado")
}
// App states
sealed class EstadoAplicacion {
    object Cargando: EstadoAplicacion()
    object Lista: EstadoAplicacion()
    data class Error(val mensaje: String): EstadoAplicacion()
}
// Location Optional
data class Ubicacion (
    val latitud: Double,
    val longitud: Double,
    val descripcion: String? = null
)

// Main Model
data class Recordatorio(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val categoria: Categoria,
    val prioridad: Prioridad,
    val estado: EstadoRecordatorio,
    val fechaVencimiento: LocalDateTime? = null,
    val ubicacion: Ubicacion? = null,
    val aviso: LocalDateTime? = null,
    val sincronizado: Boolean = false
)
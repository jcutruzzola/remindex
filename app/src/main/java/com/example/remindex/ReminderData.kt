package com.example.remindex

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

object ReminderData {
    // List -> Conserva recordatorios en un orden definido
    val recordatorios: List<Recordatorio> = listOf(
        Recordatorio(
            id = 1,
            titulo = "Entregar TP",
            descripcion = "Realizar y terminar los tps pendientes",
            categoria = Categoria.Estudios,
            prioridad = Prioridad.Alta,
            estado = EstadoRecordatorio.Pendiente,
            fechaVencimiento = LocalDateTime.now().plusDays(1),
            sincronizado = false
        ),

        Recordatorio(
            id = 2,
            titulo = "Preparar examen",
            descripcion = "Repasar los contenidos para el próximo examen",
            categoria = Categoria.Estudios,
            prioridad = Prioridad.Alta,
            estado = EstadoRecordatorio.Pendiente,
            fechaVencimiento = LocalDateTime.now().plusDays(3),
            sincronizado = false
        ),

        Recordatorio(
            id = 3,
            titulo = "Turno médico",
            descripcion = "Asistir al control médico de rutina",
            categoria = Categoria.Salud,
            prioridad = Prioridad.Alta,
            estado = EstadoRecordatorio.Pendiente,
            fechaVencimiento = LocalDateTime.now().plusDays(5),
            ubicacion = Ubicacion(
                latitud = -53.7877,
                longitud = -67.7095,
                descripcion = "Centro médico - Río Grande"
            ),
            sincronizado = false
        ),
        Recordatorio(
            id = 4,
            titulo = "Reunión de trabajo",
            descripcion = "Participar en la reunión semanal del equipo",
            categoria = Categoria.Trabajo,
            prioridad = Prioridad.Media,
            estado = EstadoRecordatorio.Pendiente,
            fechaVencimiento = LocalDateTime.now().plusDays(2),
            sincronizado = false
        ),
        Recordatorio(
            id = 5,
            titulo = "Comprar alimentos",
            descripcion = "Realizar las compras semanales en el supermercado",
            categoria = Categoria.Personal,
            prioridad = Prioridad.Baja,
            estado = EstadoRecordatorio.Pendiente,
            fechaVencimiento = LocalDateTime.now().plusDays(4),
            ubicacion = Ubicacion(
                latitud = -53.7912,
                longitud = -67.7028,
                descripcion = "Supermercado - Río Grande"
            ),
            sincronizado = false
        ),
        Recordatorio(
            id = 6,
            titulo = "Realizar actividad física",
            descripcion = "Completar una sesión de entrenamiento de fuerza",
            categoria = Categoria.Salud,
            prioridad = Prioridad.Media,
            estado = EstadoRecordatorio.Pendiente,
            fechaVencimiento = LocalDateTime.now().plusDays(1),
            sincronizado = false
        )
    )


    // SET: Evita categorias duplicadas
    val categorias: Set<Categoria> = recordatorios.map { it.categoria }.toSet()

//    MAP: Asocia cada categoria con una etiqueta y un color
    val estiloCategoria: Map<Categoria, Pair<String, Color>> = mapOf(
    Categoria.Personal to ("Personal" to Color(0xFF4B75FF)),
    Categoria.Trabajo to ("Trabajo" to Color(0xFFD2E673)),
    Categoria.Salud to ("Salud" to Color(0xFFA62E29)),
    Categoria.Estudios to ("Estudios" to Color(0xFFE6E259))

    )

    //Filtro por estado
    fun porEstado(
        estado: EstadoRecordatorio): List<Recordatorio>{
        return recordatorios.filter{ it.estado == estado}
    }

    //Filtro por Priodidad
    fun porPrioridad(
        prioridad: Prioridad
    ): List<Recordatorio>{
        return recordatorios.filter{it.prioridad == prioridad}
    }

    //Devuelve unicamente los recordatorios pendientes
    fun pendientes(): List<Recordatorio> {
        return porEstado(EstadoRecordatorio.Pendiente)
    }




}
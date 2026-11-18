
package com.example.remindex.ui.state


import com.example.remindex.Categoria
import com.example.remindex.Prioridad

data class ReminderFormUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: Categoria? = null,
    val prioridad: Prioridad? = null,

    val errorTitulo: String? = null,
    val errorDescripcion: String? = null,
    val errorCategoria: String? = null,
    val errorPrioridad: String? = null,

    val enviando: Boolean = false,
    val guardadoExitosamente: Boolean = false,
    val errorGeneral: String? = null
) {
    val esValido: Boolean
        get() = titulo.trim().length >= 4 &&
                descripcion.length <= 200 &&
                categoria != null &&
                categoria in Categoria.options &&
                prioridad != null &&
                prioridad in Prioridad.options
}

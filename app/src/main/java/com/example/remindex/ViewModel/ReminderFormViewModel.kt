
package com.example.remindex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindex.Categoria
import com.example.remindex.EstadoRecordatorio
import com.example.remindex.Prioridad
import com.example.remindex.Recordatorio
import com.example.remindex.data.IReminderRepository
import com.example.remindex.data.ReminderRepository
import com.example.remindex.ui.state.ReminderFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReminderFormViewModel(
    private val repository: IReminderRepository = ReminderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReminderFormUiState())
    val uiState = _uiState.asStateFlow()

    fun onTituloChange(valor: String) {
        _uiState.value = _uiState.value.copy(
            titulo = valor,
            errorTitulo = if (valor.trim().length >= 4) {
                null
            } else {
                _uiState.value.errorTitulo
            },
            errorGeneral = null
        )
    }

    fun onDescripcionChange(valor: String) {
        if (valor.length <= 200) {
            _uiState.value = _uiState.value.copy(
                descripcion = valor,
                errorDescripcion = null,
                errorGeneral = null
            )
        }
    }

    fun onCategoriaChange(valor: Categoria) {
        _uiState.value = _uiState.value.copy(
            categoria = valor,
            errorCategoria = null,
            errorGeneral = null
        )
    }

    fun onPrioridadChange(valor: Prioridad) {
        _uiState.value = _uiState.value.copy(
            prioridad = valor,
            errorPrioridad = null,
            errorGeneral = null
        )
    }

    fun guardarRecordatorio() {
        val estadoActual = _uiState.value

        if (!estadoActual.esValido) {
            _uiState.value = estadoActual.copy(
                errorTitulo = if (estadoActual.titulo.trim().length < 4) {
                    "El título debe tener al menos 4 caracteres."
                } else null,

                errorDescripcion = if (estadoActual.descripcion.length > 200) {
                    "La descripción no puede superar los 200 caracteres."
                } else null,

                errorCategoria = if (
                    estadoActual.categoria == null ||
                    estadoActual.categoria !in Categoria.options
                ) {
                    "Seleccioná una categoría."
                } else null,

                errorPrioridad = if (
                    estadoActual.prioridad == null ||
                    estadoActual.prioridad !in Prioridad.options
                ) {
                    "Seleccioná una prioridad."
                } else null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                enviando = true,
                errorGeneral = null
            )

            try {
                val nuevoId = repository.obtenerProximoId()

                val nuevoRecordatorio = Recordatorio(
                    id = nuevoId,
                    titulo = estadoActual.titulo.trim(),
                    descripcion = estadoActual.descripcion.trim(),
                    categoria = estadoActual.categoria!!,
                    prioridad = estadoActual.prioridad!!,
                    estado = EstadoRecordatorio.Pendiente
                )

               repository.guardarRecordatorio(nuevoRecordatorio)

                _uiState.value = _uiState.value.copy(
                    enviando = false,
                    guardadoExitosamente = true
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    enviando = false,
                    errorGeneral = e.message
                        ?: "No se pudo guardar el recordatorio."
                )
            }
        }
    }

    fun limpiarFormulario() {
        _uiState.value = ReminderFormUiState()
    }
}

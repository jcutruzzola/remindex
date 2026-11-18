
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
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReminderFormViewModel(
    private val repository: IReminderRepository = ReminderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ReminderFormUiState())
    val uiState: StateFlow<ReminderFormUiState> = _uiState.asStateFlow()

    // Cada evento produce una nueva instancia del estado y vuelve a validar
    // el formulario, para que los mensajes acompañen al botón deshabilitado.
    fun onTituloChange(valor: String) {
        _uiState.update {
            it.copy(titulo = valor, errorGeneral = null).validado()
        }
    }

    fun onDescripcionChange(valor: String) {
        _uiState.update {
            it.copy(descripcion = valor, errorGeneral = null).validado()
        }
    }

    fun onCategoriaChange(valor: Categoria) {
        _uiState.update {
            it.copy(categoria = valor, errorGeneral = null).validado()
        }
    }

    fun onPrioridadChange(valor: Prioridad) {
        _uiState.update {
            it.copy(prioridad = valor, errorGeneral = null).validado()
        }
    }

    fun guardarRecordatorio() {
        val estadoActual = _uiState.value

        if (estadoActual.enviando) return

        if (!estadoActual.esValido) {
            _uiState.update { it.validado() }
            return
        }

        _uiState.update {
            it.copy(enviando = true, errorGeneral = null)
        }

        viewModelScope.launch {
            try {
                val nuevoRecordatorio = Recordatorio(
                    id = repository.obtenerProximoId(),
                    titulo = estadoActual.titulo.trim(),
                    descripcion = estadoActual.descripcion.trim(),
                    categoria = estadoActual.categoria!!,
                    prioridad = estadoActual.prioridad!!,
                    estado = EstadoRecordatorio.Pendiente
                )

                repository.guardarRecordatorio(nuevoRecordatorio)

                _uiState.update {
                    it.copy(enviando = false, guardadoExitosamente = true)
                }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        enviando = false,
                        errorGeneral = e.message
                            ?: "No se pudo guardar el recordatorio."
                    )
                }
            }
        }
    }

    fun limpiarFormulario() {
        _uiState.update { ReminderFormUiState() }
    }

    private fun ReminderFormUiState.validado(): ReminderFormUiState = copy(
        errorTitulo = if (titulo.trim().length < 4) {
            "El título debe tener al menos 4 caracteres."
        } else null,

        errorDescripcion = if (descripcion.length > 200) {
            "La descripción no puede superar los 200 caracteres."
        } else null,

        errorCategoria = if (categoria == null || categoria !in Categoria.options) {
            "Seleccioná una categoría."
        } else null,

        errorPrioridad = if (prioridad == null || prioridad !in Prioridad.options) {
            "Seleccioná una prioridad."
        } else null
    )
}

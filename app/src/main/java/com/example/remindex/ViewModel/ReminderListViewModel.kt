
package com.example.remindex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remindex.data.IReminderRepository
import com.example.remindex.data.ReminderRepository
import com.example.remindex.ui.state.ReminderListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReminderListViewModel(
    private val repository: IReminderRepository =  ReminderRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ReminderListUiState>(
        ReminderListUiState.Cargando
    )

    val uiState = _uiState.asStateFlow()

    init {
        cargarRecordatorios()
    }
    fun cargarRecordatorios() {
        viewModelScope.launch {
            _uiState.value = ReminderListUiState.Cargando

            try {
                val recordatorios = repository.obtenerRecordatorios()

                _uiState.value = if (recordatorios.isEmpty()) {
                    ReminderListUiState.Vacio
                } else {
                    ReminderListUiState.ConDatos(recordatorios)
                }

            } catch (e: Exception) {
                _uiState.value = ReminderListUiState.Error(
                    e.message ?: "Ocurrió un error al cargar los recordatorios."
                )
            }
        }
    }

    fun reintentar() {
        repository.activarErrorSimulado(false)
        cargarRecordatorios()
    }

    fun simularEstadoVacio() {
        repository.vaciarRecordatorios()
        cargarRecordatorios()
    }

    fun simularEstadoError() {
        repository.activarErrorSimulado(true)
        cargarRecordatorios()
    }

    fun restaurarDatos() {
        repository.restaurarRecordatorios()
        cargarRecordatorios()
    }
}

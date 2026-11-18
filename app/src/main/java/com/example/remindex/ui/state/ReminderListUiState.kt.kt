package com.example.remindex.ui.state

import com.example.remindex.Recordatorio

sealed interface ReminderListUiState {

    data object Cargando: ReminderListUiState

    data object Vacio: ReminderListUiState


    data class ConDatos(
        val recordatorios: List<Recordatorio>
    ) : ReminderListUiState

    data class Error(
        val mensaje: String
    ): ReminderListUiState
}
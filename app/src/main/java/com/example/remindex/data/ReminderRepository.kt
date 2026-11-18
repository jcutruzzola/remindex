
package com.example.remindex.data

import com.example.remindex.Recordatorio
import com.example.remindex.ReminderData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


object ReminderRepository : IReminderRepository {

    private val _recordatorios = MutableStateFlow(
        ReminderData.recordatorios.toList()
    )

    val recordatorios: StateFlow<List<Recordatorio>> =
        _recordatorios.asStateFlow()

    private var simularError = false

    override suspend fun obtenerRecordatorios(): List<Recordatorio> {
        delay(1000)

        if (simularError) {
            throw Exception("No se pudieron cargar los recordatorios.")
        }

        return _recordatorios.value
    }

    override fun obtenerProximoId(): Int {

        return (_recordatorios.value.maxOfOrNull { it.id } ?: 0) + 1
    }

   override suspend fun guardarRecordatorio(recordatorio: Recordatorio) {
        delay(1000)

        if (simularError) {
            throw Exception("No se pudo guardar el recordatorio.")
        }

        _recordatorios.value =
            _recordatorios.value + recordatorio
    }

    override fun activarErrorSimulado(activar: Boolean) {
        simularError = activar
    }

   override fun vaciarRecordatorios() {
        _recordatorios.value = emptyList()
    }

    override fun restaurarRecordatorios() {
        _recordatorios.value = ReminderData.recordatorios.toList()
        simularError = false
    }
}

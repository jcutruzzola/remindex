package com.example.remindex.data

import com.example.remindex.Recordatorio

interface IReminderRepository {

    suspend fun obtenerRecordatorios(): List<Recordatorio>
    suspend fun guardarRecordatorio( recordatorio: Recordatorio)

    fun obtenerProximoId(): Int

    fun activarErrorSimulado(activar: Boolean)

    fun vaciarRecordatorios()

    fun restaurarRecordatorios()
}


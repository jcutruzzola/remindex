package com.example.remindex.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.remindex.Recordatorio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDetail(
    recordatorio: Recordatorio,
    onVolver: () -> Unit
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Detalle de recordatorio")
                },
                navigationIcon = {
                    TextButton(onClick = onVolver) {
                        Text("Volver")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = recordatorio.titulo,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "ID: ${recordatorio.id}"
                    )

                    Text(
                        text = "Descripcion: ${recordatorio.descripcion}"
                    )

                    Text(
                        text = "Categoria: ${recordatorio.categoria.etiqueta}"
                    )


                    Text(
                        text = "Prioridad: ${recordatorio.prioridad.etiqueta}"
                    )

                    Text(
                        text = "Estado: ${recordatorio.estado.etiqueta}"
                    )

                    Text(
                        text = "Fecha de vencimiento: ${
                            recordatorio.fechaVencimiento?.toString()
                                ?.replace("T", " ") ?: "Sin Fecha"
                        }"
                    )

                    Text(
                        text = "Aviso: ${
                            recordatorio.aviso?.toString()
                                ?.replace("T", " ") ?: "Sin Aviso"
                        }"
                    )

                    Text(
                        text = "Ubicacion: ${
                            recordatorio.ubicacion?.descripcion
                                ?: "Sin ubicacion"
                        }"
                    )

                    recordatorio.ubicacion?.let { ubicacion ->
                        Text("Latitud: ${ubicacion.latitud}")
                        Text("Longitud: ${ubicacion.longitud}")
                    }

                    Text(
                        text = "Sincronizado: ${
                            if (recordatorio.sincronizado) "Si" else "No"
                        }"
                    )
                }

            }
        }
    }
}
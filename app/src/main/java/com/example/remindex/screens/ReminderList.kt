package com.example.remindex.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remindex.EstadoRecordatorio
import com.example.remindex.RecordatorioCard
import com.example.remindex.ResumeCard
import com.example.remindex.components.UiStateContent
import com.example.remindex.ui.state.ReminderListUiState

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun ReminderListScreen(
    uiState: ReminderListUiState,
    onCreateClick: () -> Unit,
    onReminderClick: (Int) -> Unit,
    onRetry: () -> Unit,
    onSimularVacio: () -> Unit,
    onSimularError: () -> Unit,
    onRestaurarDatos: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Mis recordatorios")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick
            ) {
                Text(
                    text = "+",
                    fontSize = 24.sp
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 12.dp)
        ) {

            Text(
                text = "Organizá las tareas del día",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pruebas de estados",
                style = MaterialTheme.typography.labelMedium
            )

            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                androidx.compose.material3.TextButton(
                    onClick = onSimularVacio
                ) {
                    Text("Vacío")
                }

                androidx.compose.material3.TextButton(
                    onClick = onSimularError
                ) {
                    Text("Error")
                }

                androidx.compose.material3.TextButton(
                    onClick = onRestaurarDatos
                ) {
                    Text("Restaurar")
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Lista de tareas",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            when (uiState) {

                is ReminderListUiState.Cargando -> {

                    UiStateContent(
                        cargando = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                is ReminderListUiState.Vacio -> {

                    UiStateContent(
                        estaVacio = true,
                        onCrear = onCreateClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }

                is ReminderListUiState.ConDatos -> {

                    val recordatorios = uiState.recordatorios

                    val pendientes = recordatorios.count {
                        it.estado == EstadoRecordatorio.Pendiente
                    }

                    androidx.compose.foundation.layout.Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        ResumeCard(
                            titulo = "Total",
                            cantidad = recordatorios.size.toString(),
                            modifier = Modifier.weight(1f)
                        )

                        ResumeCard(
                            titulo = "Pendientes",
                            cantidad = pendientes.toString(),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(22.dp))

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentPadding = PaddingValues(bottom = 80.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = recordatorios,
                            key = { it.id }
                        ) { recordatorio ->

                            RecordatorioCard(
                                recordatorio = recordatorio,
                                onClick = {
                                    onReminderClick(recordatorio.id)
                                }
                            )
                        }
                    }
                }

                is ReminderListUiState.Error -> {

                    UiStateContent(
                        mensajeError = uiState.mensaje,
                        onReintentar = onRetry,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
            }
        }
    }
}

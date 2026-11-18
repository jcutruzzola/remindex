package com.example.remindex.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// Comunica los estados de carga, error o vacío. Quien lo usa decide qué estado
// mostrar con un when exhaustivo sobre el UiState, por lo que nunca se
// combinan dos situaciones a la vez.
@Composable
fun UiStateContent(
    cargando: Boolean = false,
    mensajeError: String? = null,
    estaVacio: Boolean = false,
    onReintentar: () -> Unit = {},
    onCrear: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            cargando ->  {
                CircularProgressIndicator()
            }

            mensajeError != null -> {
                Text(
                    text = mensajeError,
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onReintentar
                ) {
                    Text("Reintentar")
                }
            }

            estaVacio -> {
                Text(
                    text = "No tenés recordatorios todavía.",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onCrear
                ) {
                    Text("Crear recordatorio")
                }
            }
        }

    }
}

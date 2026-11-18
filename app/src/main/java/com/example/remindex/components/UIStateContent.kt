package com.example.remindex.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UiStateContent(
    cargando: Boolean = false,
    mensajeError: String? = null,
    estaVacio: Boolean = false,
    onReintentar: () -> Unit = {},
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when {
            cargando ->  {
                CircularProgressIndicator()
                Text(
                    text = "Cargando Recordatorios...",
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            mensajeError != null -> {
                Text(text = "Ocurrio un error: ${mensajeError}")

                Button(
                    onClick = onReintentar,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text("Reintentar")
                }
            }

            estaVacio -> {
                Text("Todavia no tenes recordatorios")
                Text("Presiona + para crear uno")
            }
        }

    }
}
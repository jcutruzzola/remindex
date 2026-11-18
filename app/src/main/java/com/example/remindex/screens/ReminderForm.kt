
package com.example.remindex.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.remindex.components.DescripcionField
import com.example.remindex.components.SelectorDeCategoria
import com.example.remindex.components.SelectorPrioridad
import com.example.remindex.components.TituloField

import com.example.remindex.ui.state.ReminderFormUiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderFormScreen(
    uiState: ReminderFormUiState,
    onTituloChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onCategoriaChange: (com.example.remindex.Categoria) -> Unit,
    onPrioridadChange: (com.example.remindex.Prioridad) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Nuevo recordatorio")
                },
                navigationIcon = {
                    TextButton(
                        onClick = onCancelar,
                        enabled = !uiState.enviando
                    ) {
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

            TituloField(
                value = uiState.titulo,
                onValueChange = onTituloChange,
                errorMessage = uiState.errorTitulo
            )

            DescripcionField(
                value = uiState.descripcion,
                onValueChange = onDescripcionChange,
                errorMessage = uiState.errorDescripcion
            )

            SelectorDeCategoria(
                categoria = uiState.categoria,
                onCategoriaChange = onCategoriaChange,
                errorMessage = uiState.errorCategoria
            )

            SelectorPrioridad(
                prioridad = uiState.prioridad,
                onPrioridadChange = onPrioridadChange,
                errorMessage = uiState.errorPrioridad
            )

            if (uiState.errorGeneral != null) {
                Text(
                    text = uiState.errorGeneral,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                TextButton(
                    onClick = onCancelar,
                    modifier = Modifier.weight(1f),
                    enabled = !uiState.enviando
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = onGuardar,
                    modifier = Modifier.weight(1f),
                    enabled = uiState.esValido && !uiState.enviando
                ) {
                    Text(
                        if (uiState.enviando) "Guardando..."
                        else "Guardar"
                    )
                }
            }

            if (uiState.enviando) {
                CircularProgressIndicator()
            }
        }
    }
}

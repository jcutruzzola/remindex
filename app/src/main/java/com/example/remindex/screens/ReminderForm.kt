
package com.example.remindex.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.remindex.Categoria
import com.example.remindex.Prioridad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
 fun ReminderForm(
    titulo: String,
    descripcion: String,
    categoria: Categoria?,
    prioridad: Prioridad?,
    onTituloChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onCategoriaChange: (Categoria) -> Unit,
    onPrioridadChange: (Prioridad) -> Unit,
    onGuardar: () -> Unit,
    onCancelar: () -> Unit,
    puedeGuardar: Boolean = true,
    guardando: Boolean = false
) {
    var mostrarCategorias by remember {
        mutableStateOf(false)
    }

    var mostrarPrioridades by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Nuevo Recordatorio")
                },
                navigationIcon = {
                    TextButton(
                        onClick = onCancelar,
                        enabled = !guardando
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

            // Título
            OutlinedTextField(
                value = titulo,
                onValueChange = onTituloChange,
                label = { Text("Título") },
                placeholder = { Text("Ej. Entregar informe") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Descripción
            OutlinedTextField(
                value = descripcion,
                onValueChange = onDescripcionChange,
                label = { Text("Descripción") },
                placeholder = {
                    Text("Describí el recordatorio")
                },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            // Selector de categoría
            Column {
                Text("Categoría")

                Box {
                    TextButton(
                        onClick = {
                            mostrarCategorias = true
                        }
                    ) {
                        Text(
                            categoria?.etiqueta
                                ?: "Seleccionar categoría"
                        )
                    }

                    DropdownMenu(
                        expanded = mostrarCategorias,
                        onDismissRequest = {
                            mostrarCategorias = false
                        }
                    ) {
                        Categoria.options.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(option.etiqueta)
                                },
                                onClick = {
                                    onCategoriaChange(option)
                                    mostrarCategorias = false
                                }
                            )
                        }
                    }
                }
            }

            // Selector de prioridad
            Column {
                Text("Prioridad")

                Box {
                    TextButton(
                        onClick = {
                            mostrarPrioridades = true
                        }
                    ) {
                        Text(prioridad?.etiqueta.toString())
                    }

                    DropdownMenu(
                        expanded = mostrarPrioridades,
                        onDismissRequest = {
                            mostrarPrioridades = false
                        }
                    ) {
                        Prioridad.options.forEach { option ->
                            DropdownMenuItem(
                                text = {
                                    Text(option.etiqueta)
                                },
                                onClick = {
                                    onPrioridadChange(option)
                                    mostrarPrioridades = false
                                }
                            )
                        }
                    }
                }
            }

            // Botones de acción
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextButton(
                    onClick = onCancelar,
                    modifier = Modifier.weight(1f),
                    enabled = !guardando
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = onGuardar,
                    modifier = Modifier.weight(1f),
                    enabled = puedeGuardar && !guardando
                ) {
                    Text(
                        if (guardando) "Guardando..."
                        else "Guardar"
                    )
                }
            }
        }
    }
}

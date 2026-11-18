package com.example.remindex.components

import android.app.appsearch.Migrator
import android.net.TetheringManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.error
import androidx.compose.ui.semantics.semantics
import com.example.remindex.Categoria
import com.example.remindex.Prioridad
import kotlin.math.exp


@Composable
fun TituloField (
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    modifier: Modifier = Modifier

){
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Titulo") },
        placeholder = {Text("Ej. Entregar Informe")},
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (errorMessage != null) {
                    Modifier.semantics{error(errorMessage)}
                } else {
                    Modifier
                }
            ),
        singleLine = true,
        isError = errorMessage != null,
        supportingText = {
            if(errorMessage!=null){
                Text(errorMessage)
            }
        }
    )
}


@Composable
fun DescripcionField (
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    modifier: Modifier = Modifier

) {

    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value=value,
            onValueChange = onValueChange,
            label = {Text("Descripcion")},
            placeholder = { Text("Describi el recordatorio")},
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (errorMessage != null) {
                        Modifier.semantics{
                            error(errorMessage)
                        }
                    } else {
                        Modifier
                    }
                ),

            minLines = 3,
            isError = errorMessage != null,
            supportingText = {
                Text(errorMessage ?: "${value.length}/200 caracteres")
            }
        )
    }
}


@Composable
fun SelectorDeCategoria(
    categoria: Categoria?,
    onCategoriaChange: (Categoria) -> Unit,
    errorMessage: String? = null,
    modifier: Modifier = Modifier

){
    var expandido by remember { mutableStateOf(false) }

    Column(modifier = Modifier) {
        Text("Categoria")

        TextButton(onClick = {expandido = true}) {
            Text(categoria?.etiqueta ?: "Seleccionar Categoria")
        }

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = {expandido = false}
        ) {
            Categoria.options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.etiqueta)},
                    onClick = {
                        onCategoriaChange(option)
                        expandido = false
                    }
                )
            }
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage,
                modifier = Modifier.semantics{
                    error(errorMessage)
                }
            )
        }
    }

}


@Composable
fun SelectorPrioridad(
    prioridad: Prioridad?,
    onPrioridadChange: (Prioridad) -> Unit,
    errorMessage: String? = null,
    modifier: Modifier = Modifier
){
    var expandido by remember {mutableStateOf(false)}

    Column(modifier = Modifier) {
        Text("Prioridad")

        TextButton(onClick = {expandido = true}) {
            Text(prioridad?.etiqueta ?: "Seleccionar Prioridad")
        }

        DropdownMenu(expanded = expandido, onDismissRequest = {expandido = false}) {
            Prioridad.options.forEach { option ->
                DropdownMenuItem(
                    text = {Text(option.etiqueta)},
                    onClick = {
                        onPrioridadChange(option)
                        expandido = false
                    }
                )
            }
        }


        if(errorMessage != null) {
            Text(
                text = errorMessage,
                modifier = Modifier.semantics{
                    error(errorMessage)
                }
            )
        }
    }
}
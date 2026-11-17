
package com.example.remindex

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.remindex.screens.ReminderListScreen
import com.example.remindex.screens.ReminderForm
import com.example.remindex.screens.ReminderDetail



@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val recordatorios = remember {
        mutableStateListOf<Recordatorio>().apply {
            addAll(ReminderData.recordatorios)
        }
    }

    NavHost(
        navController = navController,
        startDestination = "Listado"
    ) {

        // ========================================
        // PANTALLA DE LISTADO
        // ========================================

        composable("Listado") {
            ReminderListScreen(
                recordatorios = recordatorios,

                onCreateClick = {
                    navController.navigate("Crear")
                },

                onReminderClick = { id ->
                    navController.navigate("detalle/$id")
                }
            )
        }

        // ========================================
        // PANTALLA DE CREACIÓN
        // ========================================

        composable("Crear") {

            var titulo by remember {
                mutableStateOf("")
            }

            var descripcion by remember {
                mutableStateOf("")
            }

            var categoria by remember {
                mutableStateOf<Categoria?>(null)
            }

            var prioridad by remember {
                mutableStateOf<Prioridad?>(null)
            }

            val puedeGuardar =
                titulo.isNotBlank() &&
                        categoria != null &&
                        prioridad != null

            ReminderForm(
                titulo = titulo,
                descripcion = descripcion,
                categoria = categoria,
                prioridad = prioridad,

                onTituloChange = {
                    titulo = it
                },

                onDescripcionChange = {
                    descripcion = it
                },

                onCategoriaChange = {
                    categoria = it
                },

                onPrioridadChange = {
                    prioridad = it
                },

                onGuardar = {

                    val categoriaSeleccionada = categoria
                    val prioridadSeleccionada = prioridad

                    if (
                        titulo.isNotBlank() &&
                        categoriaSeleccionada != null &&
                        prioridadSeleccionada != null
                    ) {

                        val nuevoRecordatorio = Recordatorio(
                            id = (recordatorios.maxOfOrNull {
                                it.id
                            } ?: 0) + 1,

                            titulo = titulo.trim(),
                            descripcion = descripcion.trim(),
                            categoria = categoriaSeleccionada,
                            prioridad = prioridadSeleccionada,
                            estado = EstadoRecordatorio.Pendiente
                        )

                        recordatorios.add(nuevoRecordatorio)

                        navController.popBackStack()
                    }
                },

                onCancelar = {
                    navController.popBackStack()
                },

                puedeGuardar = puedeGuardar
            )
        }

        // ========================================
        // PANTALLA DE DETALLE
        // ========================================

        composable(
            route = "detalle/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments
                ?.getInt("id") ?: -1

            val recordatorio = recordatorios.firstOrNull {
                it.id == id
            }

            if (recordatorio != null) {

                ReminderDetail(
                    recordatorio = recordatorio,

                    onVolver = {
                        navController.popBackStack()
                    }
                )

            } else {
                Text("No se encontró el recordatorio")
            }
        }
    }
}

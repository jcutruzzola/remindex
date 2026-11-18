
package com.example.remindex

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.remindex.screens.ReminderListScreen
import com.example.remindex.screens.ReminderFormScreen
import com.example.remindex.screens.ReminderDetail

import com.example.remindex.viewmodel.ReminderListViewModel
import com.example.remindex.viewmodel.ReminderFormViewModel

import com.example.remindex.ui.state.ReminderListUiState

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val listViewModel: ReminderListViewModel = viewModel()

    val listUiState = listViewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = "listado"
    ) {

        // PANTALLA DE LISTADO
        composable("listado") {

            ReminderListScreen(
                uiState = listUiState.value,

                onCreateClick = {
                    navController.navigate("crear")
                },

                onReminderClick = { id ->
                    navController.navigate("detalle/$id")
                },

                onRetry = {
                    listViewModel.reintentar()
                },

                onSimularVacio = {
                    listViewModel.simularEstadoVacio()
                },

                onSimularError = {
                    listViewModel.simularEstadoError()
                },

                onRestaurarDatos = {
                    listViewModel.restaurarDatos()
                }
            )
        }

        // PANTALLA DE CREACIÓN
        composable("crear") {

            val formViewModel: ReminderFormViewModel = viewModel()

            val formUiState =
                formViewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(formUiState.value.guardadoExitosamente) {
                if (formUiState.value.guardadoExitosamente) {

                    listViewModel.cargarRecordatorios()

                    navController.popBackStack()
                }
            }

            ReminderFormScreen(
                uiState = formUiState.value,

                onTituloChange = { valor ->
                    formViewModel.onTituloChange(valor)
                },

                onDescripcionChange = { valor ->
                    formViewModel.onDescripcionChange(valor)
                },

                onCategoriaChange = { categoria ->
                    formViewModel.onCategoriaChange(categoria)
                },

                onPrioridadChange = { prioridad ->
                    formViewModel.onPrioridadChange(prioridad)
                },

                onGuardar = {
                    formViewModel.guardarRecordatorio()
                },

                onCancelar = {
                    navController.popBackStack()
                }
            )
        }

        // PANTALLA DE DETALLE
        composable(
            route = "detalle/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: -1

            val recordatorios = when (val estado = listUiState.value) {
                is ReminderListUiState.ConDatos -> estado.recordatorios
                else -> emptyList()
            }

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
                Text("No se encontró el recordatorio.")
            }
        }
    }
}

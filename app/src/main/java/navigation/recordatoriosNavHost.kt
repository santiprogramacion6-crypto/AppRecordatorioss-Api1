package com.example.apprecordatorios.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.apprecordatorios.data.InMemoryRecordatorioRepository
import com.example.apprecordatorios.ui.*

@Composable
fun RecordatoriosApp() {
    val navController = rememberNavController()
    RecordatoriosNavHost(navController = navController)
}

@Composable
fun RecordatoriosNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Compartimos la misma instancia en memoria entre pantallas
    val repository = remember { InMemoryRecordatorioRepository() }

    // Instanciamos los ViewModels inyectando el repositorio compartido
    val listaViewModel: ListaRecordatoriosViewModel = viewModel {
        ListaRecordatoriosViewModel(repository)
    }
    val crearViewModel: CrearRecordatorioViewModel = viewModel {
        CrearRecordatorioViewModel(repository)
    }

    NavHost(
        navController = navController,
        startDestination = ListaRoute,
        modifier = modifier
    ) {
        // 1. Destino inicial: Listado
        composable<ListaRoute> {
            ListadoScreen(
                viewModel = listaViewModel,
                onCrearRecordatorio = {
                    navController.navigate(CrearRoute)
                },
                onRecordatorioClick = { id ->
                    navController.navigate(DetalleRoute(recordatorioId = id))
                }
            )
        }

        // 2. Destino: Formulario de Creación
        composable<CrearRoute> {
            CrearRecordatorioScreen(
                viewModel = crearViewModel,
                onGuardado = {
                    listaViewModel.cargarRecordatorios()
                    navController.popBackStack()
                },
                onCancelar = {
                    navController.popBackStack()
                }
            )
        }

        // 3. Destino: Detalle (recibe solo el ID tipado)
        composable<DetalleRoute> { backStackEntry ->
            val destino = backStackEntry.toRoute<DetalleRoute>()
            val recordatorio = listaViewModel.obtenerRecordatorioPorId(destino.recordatorioId)

            DetalleRecordatorioScreen(
                recordatorio = recordatorio,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}
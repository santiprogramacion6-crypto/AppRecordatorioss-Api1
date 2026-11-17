package com.example.apprecordatorios.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoScreen(
    viewModel: ListaRecordatoriosViewModel,
    onCrearRecordatorio: () -> Unit,
    onRecordatorioClick: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Recordatorios") },
                actions = {
                    // Botones auxiliares para probar los estados requeridos en la entrega
                    TextButton(onClick = {
                        viewModel.simularErrorCarga = false
                        viewModel.simularListaVacia = !viewModel.simularListaVacia
                        viewModel.cargarRecordatorios()
                    }) {
                        Text("Vacío")
                    }
                    TextButton(onClick = {
                        viewModel.simularListaVacia = false
                        viewModel.simularErrorCarga = !viewModel.simularErrorCarga
                        viewModel.cargarRecordatorios()
                    }) {
                        Text("Error")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onCrearRecordatorio) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Nuevo recordatorio")
            }
        }
    ) { innerPadding ->
        when (val estado = uiState) {
            is ListaRecordatoriosUiState.Cargando -> {
                EstadoListaView(
                    cargando = true,
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is ListaRecordatoriosUiState.Vacio -> {
                EstadoListaView(
                    mensajeVacio = "No hay recordatorios registrados. Presioná + para crear uno.",
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is ListaRecordatoriosUiState.Error -> {
                EstadoListaView(
                    mensajeError = estado.mensaje,
                    onReintentar = {
                        viewModel.simularErrorCarga = false
                        viewModel.cargarRecordatorios()
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is ListaRecordatoriosUiState.ConDatos -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = estado.recordatorios,
                        key = { it.id }
                    ) { recordatorio ->
                        RecordatorioCard(
                            recordatorio = recordatorio,
                            onClick = { onRecordatorioClick(recordatorio.id) }
                        )
                    }
                }
            }
        }
    }
}
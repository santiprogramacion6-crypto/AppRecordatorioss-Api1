package com.example.apprecordatorios.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.apprecordatorios.DataSource
import com.example.apprecordatorios.Prioridad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearRecordatorioScreen(
    viewModel: CrearRecordatorioViewModel,
    onGuardado: () -> Unit,
    onCancelar: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Reacciona automáticamente cuando el guardado finaliza con éxito
    LaunchedEffect(uiState.guardado) {
        if (uiState.guardado) {
            viewModel.reiniciarEstadoGuardado()
            onGuardado()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Nuevo Recordatorio") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CampoTextoControlado(
                valor = uiState.titulo,
                alCambiarValor = viewModel::actualizarTitulo,
                etiqueta = "Título *",
                mensajeError = uiState.errorTitulo
            )

            CampoTextoControlado(
                valor = uiState.descripcion,
                alCambiarValor = viewModel::actualizarDescripcion,
                etiqueta = "Descripción (opcional)",
                unaSolaLinea = false
            )

            // Selector de Categoría
            Text(text = "Categoría", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DataSource.categoriasUnicas.forEach { cat ->
                    FilterChip(
                        selected = uiState.categoria == cat,
                        onClick = { viewModel.actualizarCategoria(cat) },
                        label = { Text(cat) }
                    )
                }
            }

            // Selector de Prioridad
            Text(text = "Prioridad", style = MaterialTheme.typography.labelLarge)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Prioridad.entries.forEach { prio ->
                    FilterChip(
                        selected = uiState.prioridad == prio,
                        onClick = { viewModel.actualizarPrioridad(prio) },
                        label = { Text(prio.name) }
                    )
                }
            }

            if (uiState.errorGeneral != null) {
                Text(
                    text = uiState.errorGeneral!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancelar,
                    enabled = !uiState.enviando,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = viewModel::guardarRecordatorio,
                    enabled = uiState.formularioValido && !uiState.enviando,
                    modifier = Modifier.weight(1f)
                ) {
                    if (uiState.enviando) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}
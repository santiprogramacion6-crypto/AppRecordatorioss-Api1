package com.example.apprecordatorios.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apprecordatorios.EstadoRecordatorio
import com.example.apprecordatorios.Recordatorio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleRecordatorioScreen(
    recordatorio: Recordatorio?,
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Recordatorio") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (recordatorio == null) {
            EstadoListaView(
                mensajeError = "No se encontró el recordatorio solicitado.",
                onReintentar = onVolver,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = recordatorio.titulo,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        HorizontalDivider()
                        Text(
                            text = "ID de registro: #${recordatorio.id}",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "Categoría: ${recordatorio.categoria}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Prioridad: ${recordatorio.prioridad.name}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        val estadoTexto = when (recordatorio.estado) {
                            is EstadoRecordatorio.Pendiente -> "Pendiente"
                            is EstadoRecordatorio.Completado -> "Completado"
                        }
                        Text(
                            text = "Estado actual: $estadoTexto",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Descripción: ${recordatorio.descripcion ?: "Sin descripción adicional."}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Button(
                    onClick = onVolver,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver al listado")
                }
            }
        }
    }
}
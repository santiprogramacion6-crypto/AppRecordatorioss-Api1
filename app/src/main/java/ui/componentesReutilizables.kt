package com.example.apprecordatorios.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apprecordatorios.EstadoRecordatorio
import com.example.apprecordatorios.Prioridad
import com.example.apprecordatorios.Recordatorio

// 1. Componente reutilizable para representar cada recordatorio
@Composable
fun RecordatorioCard(
    recordatorio: Recordatorio,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = recordatorio.titulo,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Categoría: ${recordatorio.categoria}",
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Prioridad: ${recordatorio.prioridad.name}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                val textoEstado = when (recordatorio.estado) {
                    is EstadoRecordatorio.Pendiente -> "Pendiente"
                    is EstadoRecordatorio.Completado -> "Completado"
                }
                Text(
                    text = "Estado: $textoEstado",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

// 2. Componente reutilizable para comunicar Carga, Vacío o Error con reintento
@Composable
fun EstadoListaView(
    cargando: Boolean = false,
    mensajeVacio: String? = null,
    mensajeError: String? = null,
    onReintentar: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            cargando -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CircularProgressIndicator()
                    Text(
                        text = "Cargando recordatorios...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            mensajeError != null -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = mensajeError,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                    if (onReintentar != null) {
                        Button(onClick = onReintentar) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            mensajeVacio != null -> {
                Text(
                    text = mensajeVacio,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// 3. Componente controlado para campos de texto del formulario
@Composable
fun CampoTextoControlado(
    valor: String,
    alCambiarValor: (String) -> Unit,
    etiqueta: String,
    mensajeError: String? = null,
    unaSolaLinea: Boolean = true,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = valor,
        onValueChange = alCambiarValor,
        label = { Text(etiqueta) },
        isError = mensajeError != null,
        supportingText = {
            if (mensajeError != null) {
                Text(text = mensajeError)
            }
        },
        singleLine = unaSolaLinea,
        modifier = modifier.fillMaxWidth()
    )
}

package com.example.apprecordatorios

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aquí usamos el tema por defecto de tu app
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                // Llamamos a nuestra pantalla principal pasándole los datos simulados
                PantallaRecordatorios(recordatorios = DataSource.recordatoriosSimulados)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRecordatorios(recordatorios: List<Recordatorio>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Recordatorios") }
            )
        }
    ) { innerPadding ->
        // LazyColumn muestra la lista de forma eficiente (como un RecyclerView moderno)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recordatorios) { recordatorio ->
                TarjetaRecordatorio(recordatorio = recordatorio)
            }
        }
    }
}

@Composable
fun TarjetaRecordatorio(recordatorio: Recordatorio) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = recordatorio.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Categoría: ${recordatorio.categoria}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Prioridad: ${recordatorio.prioridad}", style = MaterialTheme.typography.bodySmall)

            val estadoTexto = if (recordatorio.estado is EstadoRecordatorio.Pendiente) "Pendiente" else "Completado"
            Text(text = "Estado: $estadoTexto", style = MaterialTheme.typography.bodySmall)

            if (!recordatorio.descripcion.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Nota: ${recordatorio.descripcion}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
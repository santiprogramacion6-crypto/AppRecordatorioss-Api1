package com.example.apprecordatorios.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apprecordatorios.Recordatorio
import com.example.apprecordatorios.data.InMemoryRecordatorioRepository
import com.example.apprecordatorios.data.RecordatorioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListaRecordatoriosViewModel(
    private val repository: RecordatorioRepository = InMemoryRecordatorioRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListaRecordatoriosUiState>(ListaRecordatoriosUiState.Cargando)
    val uiState: StateFlow<ListaRecordatoriosUiState> = _uiState.asStateFlow()

    // Banderas para probar los estados requeridos en la entrega
    var simularErrorCarga: Boolean = false
    var simularListaVacia: Boolean = false

    init {
        cargarRecordatorios()
    }

    fun cargarRecordatorios() {
        viewModelScope.launch {
            _uiState.value = ListaRecordatoriosUiState.Cargando
            try {
                val datos = repository.obtenerRecordatorios(
                    simularError = simularErrorCarga,
                    simularVacio = simularListaVacia
                )
                _uiState.value = if (datos.isEmpty()) {
                    ListaRecordatoriosUiState.Vacio
                } else {
                    ListaRecordatoriosUiState.ConDatos(datos)
                }
            } catch (e: Exception) {
                _uiState.value = ListaRecordatoriosUiState.Error(
                    e.message ?: "Error desconocido al cargar los recordatorios."
                )
            }
        }
    }

    // Busca un recordatorio específico en el estado actual para la pantalla de detalle
    fun obtenerRecordatorioPorId(id: Int): Recordatorio? {
        val estadoActual = _uiState.value
        return if (estadoActual is ListaRecordatoriosUiState.ConDatos) {
            estadoActual.recordatorios.find { it.id == id }
        } else {
            null
        }
    }
}
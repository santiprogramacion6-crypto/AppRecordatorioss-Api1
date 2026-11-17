package com.example.apprecordatorios.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apprecordatorios.EstadoRecordatorio
import com.example.apprecordatorios.Prioridad
import com.example.apprecordatorios.Recordatorio
import com.example.apprecordatorios.data.InMemoryRecordatorioRepository
import com.example.apprecordatorios.data.RecordatorioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CrearRecordatorioViewModel(
    private val repository: RecordatorioRepository = InMemoryRecordatorioRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CrearRecordatorioUiState())
    val uiState: StateFlow<CrearRecordatorioUiState> = _uiState.asStateFlow()

    fun actualizarTitulo(nuevoTitulo: String) {
        val error = if (nuevoTitulo.trim().length < 4) {
            "Ingresá al menos cuatro caracteres"
        } else {
            null
        }
        _uiState.update {
            it.copy(titulo = nuevoTitulo, errorTitulo = error, errorGeneral = null)
        }
    }

    fun actualizarDescripcion(nuevaDescripcion: String) {
        _uiState.update { it.copy(descripcion = nuevaDescripcion) }
    }

    fun actualizarCategoria(nuevaCategoria: String) {
        _uiState.update { it.copy(categoria = nuevaCategoria) }
    }

    fun actualizarPrioridad(nuevaPrioridad: Prioridad) {
        _uiState.update { it.copy(prioridad = nuevaPrioridad) }
    }

    fun guardarRecordatorio() {
        val estadoActual = _uiState.value
        if (!estadoActual.formularioValido || estadoActual.enviando) return

        viewModelScope.launch {
            _uiState.update { it.copy(enviando = true, errorGeneral = null) }
            try {
                val nuevoRegistro = Recordatorio(
                    id = 0, // El repositorio asigna el ID real
                    titulo = estadoActual.titulo.trim(),
                    categoria = estadoActual.categoria,
                    prioridad = estadoActual.prioridad,
                    estado = EstadoRecordatorio.Pendiente,
                    descripcion = estadoActual.descripcion.trim().ifEmpty { null }
                )
                repository.guardarRecordatorio(nuevoRegistro)
                _uiState.update { it.copy(enviando = false, guardado = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        enviando = false,
                        errorGeneral = e.message ?: "No se pudo guardar el recordatorio"
                    )
                }
            }
        }
    }

    fun reiniciarEstadoGuardado() {
        _uiState.update { CrearRecordatorioUiState() }
    }
}
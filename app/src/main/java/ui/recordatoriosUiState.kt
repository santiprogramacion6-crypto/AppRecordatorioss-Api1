package com.example.apprecordatorios.ui

import com.example.apprecordatorios.Prioridad
import com.example.apprecordatorios.Recordatorio

// Estados mutuamente excluyentes para la pantalla de Listado
sealed interface ListaRecordatoriosUiState {
    object Cargando : ListaRecordatoriosUiState
    object Vacio : ListaRecordatoriosUiState
    data class ConDatos(val recordatorios: List<Recordatorio>) : ListaRecordatoriosUiState
    data class Error(val mensaje: String) : ListaRecordatoriosUiState
}

// Estado inmutable para la pantalla de Creación
data class CrearRecordatorioUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: String = "Trabajo",
    val prioridad: Prioridad = Prioridad.MEDIA,
    val errorTitulo: String? = null,
    val enviando: Boolean = false,
    val guardado: Boolean = false,
    val errorGeneral: String? = null
) {
    // Propiedad calculada para habilitar o deshabilitar el botón de guardar
    val formularioValido: Boolean
        get() = titulo.trim().length >= 4 && categoria.isNotBlank()
}
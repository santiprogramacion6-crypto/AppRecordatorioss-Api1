package com.example.apprecordatorios.data

import com.example.apprecordatorios.EstadoRecordatorio
import com.example.apprecordatorios.Prioridad
import com.example.apprecordatorios.Recordatorio
import kotlinx.coroutines.delay

// 1. Interfaz del repositorio (abstracción)
interface RecordatorioRepository {
    suspend fun obtenerRecordatorios(simularError: Boolean = false, simularVacio: Boolean = false): List<Recordatorio>
    suspend fun obtenerPorId(id: Int): Recordatorio?
    suspend fun guardarRecordatorio(recordatorio: Recordatorio, simularError: Boolean = false)
}

// 2. Implementación en memoria con retardo simulado
class InMemoryRecordatorioRepository : RecordatorioRepository {

    private val listaInterna = mutableListOf(
        Recordatorio(1, "Revisar informe mensual", "Trabajo", Prioridad.ALTA, EstadoRecordatorio.Pendiente, "Entregar antes del viernes"),
        Recordatorio(2, "Comprar medicamentos", "Salud", Prioridad.MEDIA, EstadoRecordatorio.Pendiente),
        Recordatorio(3, "Clase de programación mobile", "Estudio", Prioridad.ALTA, EstadoRecordatorio.Completado, "Repasar ViewModel y StateFlow")
    )

    override suspend fun obtenerRecordatorios(
        simularError: Boolean,
        simularVacio: Boolean
    ): List<Recordatorio> {
        delay(1000) // Simula tiempo de carga sin bloquear la interfaz
        if (simularError) {
            throw Exception("No se pudieron cargar los recordatorios. Error de conexión simulado.")
        }
        if (simularVacio) {
            return emptyList()
        }
        return listaInterna.toList()
    }

    override suspend fun obtenerPorId(id: Int): Recordatorio? {
        delay(300)
        return listaInterna.find { it.id == id }
    }

    override suspend fun guardarRecordatorio(recordatorio: Recordatorio, simularError: Boolean) {
        delay(1000) // Simula guardado asincrónico
        if (simularError) {
            throw Exception("Error al guardar el recordatorio. Intenta nuevamente.")
        }
        val nuevoId = (listaInterna.maxOfOrNull { it.id } ?: 0) + 1
        listaInterna.add(recordatorio.copy(id = nuevoId))
    }
}
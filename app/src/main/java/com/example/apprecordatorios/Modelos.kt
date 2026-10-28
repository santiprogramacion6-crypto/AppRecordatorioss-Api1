package com.example.apprecordatorios
// Uso un enum para las prioridades (valores constantes y fijos)
enum class Prioridad {
    ALTA, MEDIA, BAJA
}

// Uso una sealed class (clase sellada) para los estados del recordatorio.
sealed class EstadoRecordatorio {
    object Pendiente : EstadoRecordatorio()
    object Completado : EstadoRecordatorio()
}

// (clase de datos) inmutable (val) para agrupar los atributos
data class Recordatorio(
    val id: Int,
    val titulo: String,
    val categoria: String,
    val prioridad: Prioridad,
    val estado: EstadoRecordatorio,
    val descripcion: String? = null // Dato opcional que por defecto es nulo
)
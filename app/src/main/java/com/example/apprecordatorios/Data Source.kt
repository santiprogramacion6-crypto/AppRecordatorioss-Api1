package com.example.apprecordatorios
object DataSource {

    // Set: Almacena categorías únicas garantizando que no existan duplicados
    val categoriasUnicas: Set<String> = setOf("Trabajo", "Personal", "Salud", "Estudio")

    // Map: Asocia cada categoría con una etiqueta o color descriptivo (clave -> valor)
    val mapaColoresCategorias: Map<String, String> = mapOf(
        "Trabajo" to "Azul",
        "Personal" to "Verde",
        "Salud" to "Rojo",
        "Estudio" to "Naranja"
    )

    // List: Almacena la lista principal de objetos de tipo recordatorio
    val recordatoriosSimulados: List<Recordatorio> = listOf(
        Recordatorio(1, "Revisar informe mensual", "Trabajo", Prioridad.ALTA, EstadoRecordatorio.Pendiente, "Entregar antes del viernes"),
        Recordatorio(2, "Comprar medicamentos", "Salud", Prioridad.MEDIA, EstadoRecordatorio.Pendiente),
        Recordatorio(3, "Clase de programación mobile", "Estudio", Prioridad.ALTA, EstadoRecordatorio.Completado, "Repasar Jetpack Compose"),
        Recordatorio(4, "Pagar servicios", "Personal", Prioridad.BAJA, EstadoRecordatorio.Pendiente)
    )
}
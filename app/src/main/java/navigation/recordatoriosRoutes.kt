package com.example.apprecordatorios.navigation

import kotlinx.serialization.Serializable

@Serializable
data object ListaRoute

@Serializable
data object CrearRoute

@Serializable
data class DetalleRoute(
    val recordatorioId: Int
)
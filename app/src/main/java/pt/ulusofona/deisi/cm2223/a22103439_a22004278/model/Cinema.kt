package pt.ulusofona.deisi.cm2223.a22103439_a22004278.model

import java.io.Serializable

class Cinema(
    val id: Int,
    val nome: String,
    val latitude: Double,
    val longitude: Double,
    val morada: String,
    val localidade: String
): Serializable


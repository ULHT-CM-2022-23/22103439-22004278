package pt.ulusofona.deisi.cm2223.a22103439_a22004278.model

import java.io.Serializable
import java.util.*

class Avaliacao (
    val idUtilizador: String,
    val avalicaoUtilizador: Int,
    val dataVisualizacaoUtilizador: Date,
    val observacaoUtilizador: String,
    val filmeIMDB: Filme,
    val cinemaJSON: Cinema
): Serializable
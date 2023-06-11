package pt.ulusofona.deisi.cm2223.a22103439_a22004278.model

import java.io.Serializable
import java.util.*

class Avaliacao (
    val id: String,
    val avalicao: Int,
    val dataVisualizacao: Date,
    val observacao: String,
    val filme: Filme,
    val cinema: Cinema
): Serializable
package pt.ulusofona.deisi.cm2223.a22103439_a22004278.model

import java.io.Serializable

data class Filme(
    val id: String,
    val nome: String,
    val genero: String,
    val sinopse: String,
    val dataLancamento: String,
    val avaliacao: String,
    val link: String,
    val imagemCartaz: String,
) : Serializable
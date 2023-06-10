package pt.ulusofona.deisi.cm2223.a22103439_a22004278.model

import java.io.Serializable

data class IMDBFilme(
    val idIMDB: String,
    val nomeIMDB: String,
    val generoIMDB: String,
    val sinopseIMDB: String,
    val dataLancamentoIMDB: String,
    val avaliacaoIMDB: String,
    val linkIMDB: String,
    val imagemCartazIMDB: String,

) : Serializable
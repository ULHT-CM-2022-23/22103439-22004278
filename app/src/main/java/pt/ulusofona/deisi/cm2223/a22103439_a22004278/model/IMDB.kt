package pt.ulusofona.deisi.cm2223.a22103439_a22004278.model

import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.AvaliacaoDB

abstract class IMDB {
    abstract fun getFilmeIMDB(nomeFilme: String, onFinished: (Result<IMDBFilme>) -> Unit)
    abstract fun clearAllCharacters(onFinished: () -> Unit)

    abstract fun inserirAvaliacao(filme: IMDBFilme, avaliacao: Avaliacao, onFinished: (Result<IMDBFilme>) -> Unit)
    abstract fun getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit)
    abstract fun getAllAvaliacoes(onFinished: (Result <List<Avaliacao>>) -> Unit)

    abstract fun getCinemaJSON(onFinished: (Result <List<Cinema>>) -> Unit)
    abstract fun inserirCinemas(cinemas: List<Cinema>, onFinished: () -> Unit)
    abstract fun getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit)
    abstract fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit)
}
package pt.ulusofona.deisi.cm2223.a22103439_a22004278.model

import java.io.File

abstract class Operations {
    abstract fun getFilmeByName(nomeFilme: String, onFinished: (Result<Filme>) -> Unit)
    abstract fun clearAll(onFinished: () -> Unit)

    abstract fun getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit)
    abstract fun getAllAvaliacoes(onFinished: (Result <List<Avaliacao>>) -> Unit)
    abstract fun getTop5Avaliacoes(asc: Boolean, onFinished: (Result <List<Avaliacao>>) -> Unit)
    abstract fun getMediaAvaliacoes(onFinished: (Result <Float>) -> Unit)
    abstract fun getCountAvaliacoes(onFinished: (Result <Int>) -> Unit)
    abstract fun getAvaliacaoByFilme(idFilme: String, onFinished: (Result <Avaliacao>) -> Unit)
    abstract fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit)
    abstract fun inserirFotosAvaliacao(idAvaliacao: String, fotos: List<File>, onFinished: () -> Unit)
    abstract fun getFotosAvaliacao(idAvaliacao: String, onFinished: (Result<List<File>>) -> Unit)

    abstract fun getCinemaJSON(onFinished: (Result <List<Cinema>>) -> Unit)
    abstract fun getAllCinemas(onFinished: (Result <List<Cinema>>) -> Unit)
    abstract fun getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit)
    abstract fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit)
    abstract fun getCinemaRating(idCinema: Int, onFinished: (Result<List<CinemaRating>>) -> Unit)
    abstract fun inserirCinemas(cinemas: List<Cinema>, ratings: List<CinemaRating>, onFinished: () -> Unit)
    abstract fun clearAllCinemas(onFinished: () -> Unit)
}
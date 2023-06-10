package pt.ulusofona.deisi.cm2223.a22103439_a22004278.model

abstract class Operations {
    abstract fun getFilmeByName(nomeFilme: String, onFinished: (Result<Filme>) -> Unit)
    abstract fun clearAll(onFinished: () -> Unit)

    abstract fun getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit)
    abstract fun getAllAvaliacoes(onFinished: (Result <List<Avaliacao>>) -> Unit)
    abstract fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit)

    abstract fun getCinemaJSON(onFinished: (Result <List<Cinema>>) -> Unit)
    abstract fun getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit)
    abstract fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit)
    abstract fun inserirCinemas(cinemas: List<Cinema>, onFinished: () -> Unit)
    abstract fun clearAllCinemas(onFinished: () -> Unit)
}
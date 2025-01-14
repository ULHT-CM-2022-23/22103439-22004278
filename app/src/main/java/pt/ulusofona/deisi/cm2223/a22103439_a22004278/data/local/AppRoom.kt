package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.dao.DBOperations
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.*
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.*
import java.io.File
import java.net.URI
import java.util.*

class AppRoom(private val storage: DBOperations): Operations() {

    override fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val filmeDB = FilmeDB(
                id = filme.id,
                nome = filme.nome,
                genero = filme.genero,
                sinopse = filme.sinopse,
                dataLancamento = filme.dataLancamento,
                avaliacao = filme.avaliacao,
                link = filme.link,
                imagemCartaz = filme.imagemCartaz,
                idiomas = filme.idiomas
            )
            storage.insertFilme(filmeDB)

            val avaliacaoDB = AvaliacaoDB(
                id = avaliacao.id,
                avaliacao = avaliacao.avalicao,
                dataVisualizacao = avaliacao.dataVisualizacao.time.toLong(),
                observacoes = avaliacao.observacao,
                idFilme = filme.id,
                idCinema = avaliacao.cinema.id,
            )
            storage.insertAvaliacao(avaliacaoDB)
        }
    }

    override fun inserirFotosAvaliacao(idAvaliacao: String, fotos: List<File>, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            fotos.forEach {
                val avaliacaoFoto = AvaliacaoFotoDB(
                    idAvaliacao = idAvaliacao,
                    uriFoto = it.toURI().toString()
                )
                storage.insertFotoAvaliacao(avaliacaoFoto)
            }
            onFinished()
        }
    }

    override fun getFotosAvaliacao(idAvaliacao: String, onFinished: (Result<List<File>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val fotos = storage.getFotosAvaliacao(idAvaliacao).map {
                File(URI(it.uriFoto))
            }
            onFinished(Result.success(fotos))
        }
    }

    override fun getFilmeByName(nomeFilme: String, onFinished: (Result<Filme>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val it = storage.getFilmeById(nomeFilme)
               val filme = Filme(
                    id = it.id,
                    nome = it.nome,
                    genero = it.genero,
                    sinopse = it.sinopse,
                    dataLancamento = it.dataLancamento,
                    avaliacao = it.avaliacao,
                    link = "https://www.imdb.com/title/" + it.id,
                    imagemCartaz = it.imagemCartaz,
                   idiomas = it.idiomas
                )
            onFinished(Result.success(filme))
        }
    }

    override fun clearAll(onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            storage.deleteAllFilmes()
            onFinished()
        }
    }

    override fun getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val avaliacaoDB = storage.getAvaliacaoById(id)
            val filmeDB = storage.getFilmeById(avaliacaoDB.idFilme)
            val filme = Filme(
                id = filmeDB.id,
                nome = filmeDB.nome,
                genero = filmeDB.genero,
                sinopse = filmeDB.sinopse,
                dataLancamento = filmeDB.dataLancamento,
                avaliacao = filmeDB.avaliacao,
                link = filmeDB.link,
                imagemCartaz = filmeDB.imagemCartaz,
                idiomas = filmeDB.idiomas
            )

            val cinemaDB = storage.getCinemaById(avaliacaoDB.idCinema)
            val cinema = Cinema(
                id = cinemaDB.id,
                nome = cinemaDB.nome,
                latitude = cinemaDB.latitude,
                longitude = cinemaDB.longitude,
                morada = cinemaDB.morada,
                localidade = cinemaDB.localidade
            )

            val avaliacao = Avaliacao(
                avaliacaoDB.id,
                avaliacaoDB.avaliacao,
                Date(avaliacaoDB.dataVisualizacao),
                avaliacaoDB.observacoes,
                filme,
                cinema
            )

            onFinished(Result.success(avaliacao))
        }
    }

    override fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val avaliacaoDB = storage.getAllAvaliacoes().map {
                val filme = storage.getFilmeById(it.idFilme).let {
                    Filme(
                        id = it.id,
                        nome = it.nome,
                        genero = it.genero,
                        sinopse = it.sinopse,
                        dataLancamento = it.dataLancamento,
                        avaliacao = it.avaliacao,
                        link = it.link,
                        imagemCartaz = it.imagemCartaz,
                        idiomas = it.idiomas
                    )
                }

                val cinema = storage.getCinemaById(it.idCinema).let {
                    Cinema(
                        id = it.id,
                        nome = it.nome,
                        latitude = it.latitude,
                        longitude = it.longitude,
                        morada = it.morada,
                        localidade = it.localidade
                    )
                }

                Avaliacao(
                    id = it.id,
                    avalicao = it.avaliacao,
                    dataVisualizacao = Date(it.dataVisualizacao),
                    observacao = it.observacoes,
                    filme = filme,
                    cinema = cinema,
                )

            }
            onFinished(Result.success(avaliacaoDB))
        }
    }

    override fun getTop5Avaliacoes(asc: Boolean, onFinished: (Result<List<Avaliacao>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val avaliacoesDB = if(asc) {
                storage.getTop5AvaliacoesAsc()
            } else {
                storage.getTop5AvaliacoesDesc()
            }
            val avaliacoes : MutableList<Avaliacao> = mutableListOf()
            avaliacoesDB.map {
                val filmeDB = storage.getFilmeById(it.idFilme)
                val filme = Filme(
                    id = filmeDB.id,
                    nome = filmeDB.nome,
                    genero = filmeDB.genero,
                    sinopse = filmeDB.sinopse,
                    dataLancamento = filmeDB.dataLancamento,
                    avaliacao = filmeDB.avaliacao,
                    link = filmeDB.link,
                    imagemCartaz = filmeDB.imagemCartaz,
                    idiomas = filmeDB.idiomas
                )

                val cinemaDB = storage.getCinemaById(it.idCinema)
                val cinema = Cinema(
                    id = cinemaDB.id,
                    nome = cinemaDB.nome,
                    latitude = cinemaDB.latitude,
                    longitude = cinemaDB.longitude,
                    morada = cinemaDB.morada,
                    localidade = cinemaDB.localidade
                )

                val avaliacao = Avaliacao(
                    it.id,
                    it.avaliacao,
                    Date(it.dataVisualizacao),
                    it.observacoes,
                    filme,
                    cinema
                )

                avaliacoes.add(avaliacao)
            }

            onFinished(Result.success(avaliacoes))
        }
    }

    override fun getMediaAvaliacoes(onFinished: (Result<Float>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val media = storage.getMediaAvaliacoes()
            onFinished(Result.success(media))
        }
    }

    override fun getCountAvaliacoes(onFinished: (Result<Int>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val count = storage.getCountAvaliacoes()
            onFinished(Result.success(count))
        }
    }

    override fun getAvaliacaoByFilme(idFilme: String, onFinished: (Result<Avaliacao>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val avaliacaoDB = storage.getAvaliacaoByFilme(idFilme)
            if (avaliacaoDB == null) {
                onFinished(Result.failure(Exception("Avaliação not found")))
            } else {
                val filmeDB = storage.getFilmeById(avaliacaoDB.idFilme)
                val filme = Filme(
                    id = filmeDB.id,
                    nome = filmeDB.nome,
                    genero = filmeDB.genero,
                    sinopse = filmeDB.sinopse,
                    dataLancamento = filmeDB.dataLancamento,
                    avaliacao = filmeDB.avaliacao,
                    link = filmeDB.link,
                    imagemCartaz = filmeDB.imagemCartaz,
                    idiomas = filmeDB.idiomas
                )

                val cinemaDB = storage.getCinemaById(avaliacaoDB.idCinema)
                val cinema = Cinema(
                    id = cinemaDB.id,
                    nome = cinemaDB.nome,
                    latitude = cinemaDB.latitude,
                    longitude = cinemaDB.longitude,
                    morada = cinemaDB.morada,
                    localidade = cinemaDB.localidade
                )

                val avaliacao = Avaliacao(
                    avaliacaoDB.id,
                    avaliacaoDB.avaliacao,
                    Date(avaliacaoDB.dataVisualizacao),
                    avaliacaoDB.observacoes,
                    filme,
                    cinema
                )

                onFinished(Result.success(avaliacao))
            }
        }
    }


    override fun getCinemaJSON(onFinished: (Result<List<Cinema>>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getAllCinemas(onFinished: (Result<List<Cinema>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val cinemas = storage.getAllCinemas().map {
                Cinema(
                    id = it.id,
                    nome = it.nome,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    morada = it.morada,
                    localidade = it.localidade
                )
            }
            onFinished(Result.success(cinemas))
        }
    }

    override fun inserirCinemas(cinemas: List<Cinema>, ratings: List<CinemaRating>, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            cinemas.map {
                CinemaDB(
                    id = it.id,
                    nome = it.nome,
                    latitude = it.latitude,
                    longitude = it.longitude,
                    morada = it.morada,
                    localidade = it.localidade
                )
            }.forEach {
                storage.insertCinema(it)
            }

            ratings.map {
                CinemaRatingDB(
                    idCinema = it.idCinema,
                    categoria = it.categoria,
                    score = it.score
                )
            }.forEach {
                storage.insertCinemaRating(it)
            }
            onFinished()
        }
    }

    override fun clearAllCinemas(onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            storage.deleteAllCinemas()
            storage.deleteAllCinemaRatings()
            onFinished()
        }
    }

    override fun getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val cinemaDB = storage.getCinemaByNome(nomeCinema)
            if (cinemaDB == null) {
                onFinished(Result.failure(Exception("Cinema not found")))
            } else {
                val cinema = Cinema(
                    id = cinemaDB.id,
                    nome = cinemaDB.nome,
                    latitude = cinemaDB.latitude,
                    longitude = cinemaDB.longitude,
                    morada = cinemaDB.morada,
                    localidade = cinemaDB.localidade
                )
                onFinished(Result.success(cinema))
            }
        }
    }

    override fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val cinemaDB = storage.getCinemaById(idCinema)
            val cinema = Cinema(
                id = cinemaDB.id,
                nome = cinemaDB.nome,
                latitude = cinemaDB.latitude,
                longitude = cinemaDB.longitude,
                morada = cinemaDB.morada,
                localidade = cinemaDB.localidade
            )
            onFinished(Result.success(cinema))
        }
    }

    override fun getCinemaRating(idCinema: Int, onFinished: (Result<List<CinemaRating>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val cinemaRatings = storage.getCinemaRatings(idCinema).map {
                CinemaRating(
                    idCinema = it.idCinema,
                    categoria = it.categoria,
                    score = it.score
                )
            }
            onFinished(Result.success(cinemaRatings))
        }
    }

}
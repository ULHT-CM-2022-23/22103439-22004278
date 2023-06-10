package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.dao.DBOperations
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.AvaliacaoDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.CinemaDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.FilmeDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.*
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
                imagemCartaz = filme.imagemCartaz
            )
            storage.insertFilme(filmeDB)

            val avaliacaoDB = AvaliacaoDB(
                id = avaliacao.idUtilizador,
                avaliacao = avaliacao.avalicaoUtilizador,
                dataVisualizacao = avaliacao.dataVisualizacaoUtilizador.time.toLong(),
                observacoes = avaliacao.observacaoUtilizador,
                idFilme = filme.id,
                idCinema = avaliacao.cinemaJSON.cinema_id,
            )
            storage.insertAvaliacao(avaliacaoDB)
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
                    link = it.link,
                    imagemCartaz = it.imagemCartaz
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

            val it = storage.getFilmeById(avaliacaoDB.idFilme)
            val filme = Filme(
                id = it.id,
                nome = it.nome,
                genero = it.genero,
                sinopse = it.sinopse,
                dataLancamento = it.dataLancamento,
                avaliacao = it.avaliacao,
                link = it.link,
                imagemCartaz = it.imagemCartaz
            )

            val avaliacao = Avaliacao(
                avaliacaoDB.id,
                avaliacaoDB.avaliacao,
                Date(avaliacaoDB.dataVisualizacao),
                avaliacaoDB.observacoes,
                filme,
                Cinema(1,"Colombo")
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
                        imagemCartaz = it.imagemCartaz
                    )
                }

                Avaliacao(
                    idUtilizador = it.id,
                    avalicaoUtilizador = it.avaliacao,
                    dataVisualizacaoUtilizador = Date(it.dataVisualizacao),
                    observacaoUtilizador = it.observacoes,
                    filmeIMDB = filme,
                    cinemaJSON = Cinema(1,"Vasco da Gama"),
                )

            }
            onFinished(Result.success(avaliacaoDB))
        }
    }


    override fun getCinemaJSON(onFinished: (Result<List<Cinema>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun inserirCinemas(cinemas: List<Cinema>, onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            cinemas.map {
                CinemaDB(
                    id = it.cinema_id,
                    nome = it.cinema_name
                )
            }.forEach {
                storage.insertCinema(it)
            }
            onFinished()
        }
    }

    override fun clearAllCinemas(onFinished: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit) {
        TODO("Not yet implemented")
    }

}
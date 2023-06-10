package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.dao.IMDBOperations
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.AvaliacaoDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.CinemaDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.IMDBFilmeDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.*
import java.util.*

class IMDBRoom(private val storage: IMDBOperations): IMDB() {

    override fun inserirAvaliacao(filme: IMDBFilme,avaliacao: Avaliacao,onFinished: (Result<IMDBFilme>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val filmeDB = IMDBFilmeDB(
                idIMDB = filme.idIMDB,
                nomeIMDB = filme.nomeIMDB,
                generoIMDB = filme.generoIMDB,
                sinopseIMDB = filme.sinopseIMDB,
                dataLancamentoIMDB = filme.dataLancamentoIMDB,
                avaliacaoIMDB = filme.avaliacaoIMDB,
                linkIMDB = filme.linkIMDB,
                imagemCartazIMDB = filme.imagemCartazIMDB
            )
            storage.insert(filmeDB)

            val avaliacaoDB = AvaliacaoDB(
                idUtilizador = avaliacao.idUtilizador,
                avalicaoUtilizador = avaliacao.avalicaoUtilizador,
                dataVisualizacaoUtilizador = avaliacao.dataVisualizacaoUtilizador.time.toLong(),
                observacaoUtilizador = avaliacao.observacaoUtilizador,
                idFilme = filme.idIMDB,
                idCinema = avaliacao.cinemaJSON.cinema_id,
            )
            storage.insertAvaliacao(avaliacaoDB)
        }
    }

    override fun getFilmeIMDB(nomeFilme: String, onFinished: (Result<IMDBFilme>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val it = storage.getById(nomeFilme)
               val filme = IMDBFilme(
                    idIMDB = it.idIMDB,
                    nomeIMDB = it.nomeIMDB,
                    generoIMDB = it.generoIMDB,
                    sinopseIMDB = it.sinopseIMDB,
                    dataLancamentoIMDB = it.dataLancamentoIMDB,
                    avaliacaoIMDB = it.avaliacaoIMDB,
                    linkIMDB = it.linkIMDB,
                    imagemCartazIMDB = it.imagemCartazIMDB
                )
            onFinished(Result.success(filme))
        }
    }

    override fun clearAllCharacters(onFinished: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            storage.deleteAll()
            onFinished()
        }
    }

    override fun getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val avaliacaoDB = storage.getByIdAvaliacao(id)

            val it = storage.getById(avaliacaoDB.idFilme)
            val filme = IMDBFilme(
                idIMDB = it.idIMDB,
                nomeIMDB = it.nomeIMDB,
                generoIMDB = it.generoIMDB,
                sinopseIMDB = it.sinopseIMDB,
                dataLancamentoIMDB = it.dataLancamentoIMDB,
                avaliacaoIMDB = it.avaliacaoIMDB,
                linkIMDB = it.linkIMDB,
                imagemCartazIMDB = it.imagemCartazIMDB
            )

            val avaliacao = Avaliacao(
                avaliacaoDB.idUtilizador,
                avaliacaoDB.avalicaoUtilizador,
                Date(avaliacaoDB.dataVisualizacaoUtilizador),
                avaliacaoDB.observacaoUtilizador,
                filme,
                Cinema(1,"Colombo")
            )

            onFinished(Result.success(avaliacao))
        }
    }

    override fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val avaliacaoDB = storage.getAllAvaliacoes().map {
                val filme = storage.getById(it.idFilme).let {
                    IMDBFilme(
                        idIMDB = it.idIMDB,
                        nomeIMDB = it.nomeIMDB,
                        generoIMDB = it.generoIMDB,
                        sinopseIMDB = it.sinopseIMDB,
                        dataLancamentoIMDB = it.dataLancamentoIMDB,
                        avaliacaoIMDB = it.avaliacaoIMDB,
                        linkIMDB = it.linkIMDB,
                        imagemCartazIMDB = it.imagemCartazIMDB
                    )
                }

                Avaliacao(
                    idUtilizador = it.idUtilizador,
                    avalicaoUtilizador = it.avalicaoUtilizador,
                    dataVisualizacaoUtilizador = Date(it.dataVisualizacaoUtilizador),
                    observacaoUtilizador = it.observacaoUtilizador,
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
                    idCinema = it.cinema_id,
                    nomeCinema = it.cinema_name
                )
            }.forEach {
                storage.insertCinema(it)
            }
            onFinished()
        }
    }

    override fun getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit) {
        TODO("Not yet implemented")
    }

}
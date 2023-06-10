package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.remote.ConnectivityUtil
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Cinema
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.IMDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.IMDBFilme
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.IllegalStateException

class IMDBRepository private constructor(val local: IMDB, val remote: IMDB, val context: Context): IMDB() {
    val inputStream: InputStream = context.assets.open("cinemas.json")
    val inputStreamReader: InputStreamReader = InputStreamReader(inputStream)
    val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

    override fun clearAllCharacters(onFinished: () -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun inserirAvaliacao(filme: IMDBFilme, avaliacao: Avaliacao, onFinished: (Result<IMDBFilme>) -> Unit) {
        if (ConnectivityUtil.isOnline(context)) {
            local.inserirAvaliacao(filme, avaliacao){
                onFinished(Result.success(filme))
            }
        }
    }

    override fun getCinemaJSON(onFinished: (Result<List<Cinema>>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

            val stringBuilder = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while (line != null){
                stringBuilder.append(line)
                line = bufferedReader.readLine()
            }
            val jsonString = stringBuilder.toString()

            val jsonObject = JSONObject(jsonString)
            val jsonCinemaList = jsonObject["cinemas"] as JSONArray
            val cinemas = mutableListOf<Cinema>()
            for (i in 0 until jsonCinemaList.length()) {
                val cinema = jsonCinemaList[i] as JSONObject

                cinemas.add(
                    Cinema(
                        cinema["cinema_id"].toString().toInt(),
                        cinema["cinema_name"].toString(),

                        )
                )
            }
            local.clearAllCinemas {
                local.inserirCinemas(cinemas) {
                    onFinished(Result.success(cinemas))
                }
            }
        }
    }

    override fun inserirCinemas(cinemas: List<Cinema>, onFinished: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit) {
        local.getAvaliacao(id) { result ->
            val avaliacao = result.getOrNull()!!
            onFinished(Result.success(avaliacao))
        }
    }

    override fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        local.getAllAvaliacoes() {
            onFinished(it)
        }
    }

    override fun getFilmeIMDB(nomeFilme: String, onFinished: (Result<IMDBFilme>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            remote.getFilmeIMDB(nomeFilme){
                onFinished(it)
            }
        }
    }

    companion object {
        private var instance: IMDBRepository? = null

        fun init(local: IMDB, remote: IMDB, context: Context) {
            synchronized(this) {
                if (instance == null) {
                    instance = IMDBRepository(local, remote, context)
                }
            }
        }

        fun getInstance(): IMDBRepository {
            if (instance == null) {
                throw IllegalStateException("singleton not initialized")
            }
            return instance as IMDBRepository

        }
    }
}
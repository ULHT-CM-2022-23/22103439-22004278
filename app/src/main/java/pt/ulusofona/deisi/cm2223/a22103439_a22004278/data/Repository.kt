package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.remote.ConnectivityUtil
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Cinema
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Filme
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Operations
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Repository private constructor(val local: Operations, private val remote: Operations, val context: Context): Operations() {
    private val inputStream: InputStream = context.assets.open("cinemas.json")
    private val inputStreamReader: InputStreamReader = InputStreamReader(inputStream)
    private val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)

    override fun clearAll(onFinished: () -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) {
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
            val cinemaList = jsonObject["cinemas"] as JSONArray
            val cinemas = mutableListOf<Cinema>()
            for(i in 0 until cinemaList.length()) { // for(int i=0; i<cinemaList.length(); i++)
                val cinema = cinemaList[i] as JSONObject

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

    override fun getAllCinemas(onFinished: (Result<List<Cinema>>) -> Unit) {
        local.getAllCinemas {
            onFinished(it)
        }
    }

    override fun inserirCinemas(cinemas: List<Cinema>, onFinished: () -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun clearAllCinemas(onFinished: () -> Unit) {
        local.clearAllCinemas {
            onFinished()
        }
    }

    override fun getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit) {
        local.getCinemaByNome(nomeCinema) {
            onFinished(it)
        }
    }

    override fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit) {
        local.getCinemaById(idCinema) {
            onFinished(it)
        }
    }

    override fun getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit) {
        local.getAvaliacao(id) {
            onFinished(it)
        }
    }

    override fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        local.getAllAvaliacoes {
            onFinished(it)
        }
    }

    override fun getAvaliacaoByFilme(idFilme: String, onFinished: (Result<Avaliacao>) -> Unit) {
        local.getAvaliacaoByFilme(idFilme) {
            onFinished(it)
        }
    }

    override fun getFilmeByName(nomeFilme: String, onFinished: (Result<Filme>) -> Unit) {
        remote.getFilmeByName(nomeFilme) {
            onFinished(it)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: Repository? = null

        fun init(local: Operations, remote: Operations, context: Context) {
            synchronized(this) {
                if (instance == null) {
                    instance = Repository(local, remote, context)
                }
            }
        }

        fun getInstance(): Repository {
            if (instance == null) {
                throw IllegalStateException("singleton not initialized")
            }
            return instance as Repository

        }
    }
}
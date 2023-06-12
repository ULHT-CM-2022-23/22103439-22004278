package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data

import android.annotation.SuppressLint
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.remote.ConnectivityUtil
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.*
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader

class Repository private constructor(val local: Operations, private val remote: Operations, val context: Context): Operations() {

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

    override fun inserirFotosAvaliacao(idAvaliacao: String, fotos: List<File>, onFinished: () -> Unit) {
        local.inserirFotosAvaliacao(idAvaliacao, fotos) {
            onFinished()
        }
    }

    override fun getFotosAvaliacao(idAvaliacao: String, onFinished: (Result<List<File>>) -> Unit) {
        local.getFotosAvaliacao(idAvaliacao) {
            onFinished(it)
        }
    }

    override fun getCinemaJSON(onFinished: (Result<List<Cinema>>) -> Unit) {
        val inputStream: InputStream = context.assets.open("cinemas.json")
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        CoroutineScope(Dispatchers.IO).launch {

            val stringBuilder = StringBuilder()
            var line: String? = bufferedReader.readLine()
            while (line != null){
                stringBuilder.append(line)
                line = bufferedReader.readLine()
            }
            val jsonString = stringBuilder.toString()

            val cinemas = mutableListOf<Cinema>()
            val ratings = mutableListOf<CinemaRating>()

            val jsonObject = JSONObject(jsonString)
            val cinemaList = jsonObject["cinemas"] as JSONArray
            for(i in 0 until cinemaList.length()) { // for(int i=0; i<cinemaList.length(); i++)
                val cinema = cinemaList[i] as JSONObject
                cinemas.add(
                    Cinema(
                        cinema["cinema_id"].toString().toInt(),
                        cinema["cinema_name"].toString(),
                        cinema["latitude"].toString().toDouble(),
                        cinema["longitude"].toString().toDouble(),
                        cinema["address"].toString(),
                        cinema["county"].toString()
                        )
                )

                val ratingsList = cinema["ratings"] as JSONArray
                for(j in 0 until ratingsList.length()) {
                    val rating = ratingsList[j] as JSONObject
                    ratings.add(
                        CinemaRating(
                            cinema["cinema_id"].toString().toInt(),
                            rating["category"].toString().replaceFirstChar { it.uppercase() },
                            rating["score"].toString().toInt()
                        )
                    )
                }
            }
            local.clearAllCinemas {
                local.inserirCinemas(cinemas, ratings) {
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

    override fun inserirCinemas(cinemas: List<Cinema>, ratings: List<CinemaRating>, onFinished: () -> Unit) {
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

    override fun getCinemaRating(idCinema: Int, onFinished: (Result<List<CinemaRating>>) -> Unit) {
        local.getCinemaRating(idCinema) {
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

    override fun getTop5Avaliacoes(asc: Boolean, onFinished: (Result<List<Avaliacao>>) -> Unit) {
        local.getTop5Avaliacoes(asc) {
            onFinished(it)
        }
    }

    override fun getMediaAvaliacoes(onFinished: (Result<Float>) -> Unit) {
        local.getMediaAvaliacoes {
            onFinished(it)
        }
    }

    override fun getCountAvaliacoes(onFinished: (Result<Int>) -> Unit) {
        local.getCountAvaliacoes {
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
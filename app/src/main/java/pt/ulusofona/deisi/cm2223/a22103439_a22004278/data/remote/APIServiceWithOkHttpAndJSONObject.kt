package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.remote
import okhttp3.*
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.API_BASE_URL
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.API_TOKEN
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class APIServiceWithOkHttpAndJSONObject(
    val baseUrl: String = API_BASE_URL,
    var apiKey: String = API_TOKEN,
    val client: OkHttpClient,
) : Operations() {

    override fun getAvaliacao(id: String, onFinished: (Result<Avaliacao>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getAllAvaliacoes(onFinished: (Result<List<Avaliacao>>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getTop5Avaliacoes(asc: Boolean, onFinished: (Result<List<Avaliacao>>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getMediaAvaliacoes(onFinished: (Result<Float>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getCountAvaliacoes(onFinished: (Result<Int>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getAvaliacaoByFilme(idFilme: String, onFinished: (Result<Avaliacao>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getFilmeByName(nomeFilme: String, onFinished: (Result<Filme>) -> Unit) {
        val request: Request = Request.Builder()
            .url("$baseUrl/?type=movie&t=$nomeFilme&apikey=$apiKey")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onFinished(Result.failure(e))
            }

            override fun onResponse(call: Call, response: Response) {
                response.apply {
                    if (!response.isSuccessful) {
                        onFinished(Result.failure(IOException("Unexpected code $response")))
                    } else {
                        val body = response.body?.string()
                        if (body != null) {
                            val jsonObject = JSONObject(body)
                            if (jsonObject.getString("Response") == "False") {
                                onFinished(Result.failure(IOException("Movie not found")))
                            } else {
                                val filme = Filme(
                                    jsonObject.getString("imdbID"),
                                    jsonObject.getString("Title"),
                                    jsonObject.getString("Genre"),
                                    jsonObject.getString("Plot"),
                                    jsonObject.getString("Released"),
                                    jsonObject.getString("imdbRating"),
                                    "https://www.imdb.com/title/" + jsonObject.getString("imdbID"),
                                    jsonObject.getString("Poster"),
                                    jsonObject.getString("Language"),
                                )
                                onFinished(Result.success(filme))
                            }
                        }
                    }
                }
            }
        })
    }

    override fun getCinemaJSON(onFinished: (Result<List<Cinema>>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getAllCinemas(onFinished: (Result<List<Cinema>>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun inserirCinemas(cinemas: List<Cinema>, ratings: List<CinemaRating>, onFinished: () -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun clearAllCinemas(onFinished: () -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getCinemaByNome(nomeCinema: String, onFinished: (Result<Cinema>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getCinemaById(idCinema: Int, onFinished: (Result<Cinema>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun getCinemaRating(idCinema: Int, onFinished: (Result<List<CinemaRating>>) -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun clearAll(onFinished: () -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) {
        throw Exception("Illegal operation")
    }

}
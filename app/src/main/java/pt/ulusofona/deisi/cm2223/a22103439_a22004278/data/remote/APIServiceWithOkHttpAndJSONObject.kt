package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.remote
import okhttp3.*
import org.json.JSONObject
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.API_BASE_URL
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.API_TOKEN
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Avaliacao
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Cinema
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Operations
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.Filme
import java.io.IOException

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

                            val imdbID = jsonObject.getString("imdbID")
                            val title = jsonObject.getString("Title")
                            val genre = jsonObject.getString("Genre")
                            val plot = jsonObject.getString("Plot")
                            val released = jsonObject.getString("Released")
                            val imdbRating = jsonObject.getString("imdbRating")
                            val poster = jsonObject.getString("Poster")

                            val filme = Filme(
                                    imdbID,
                                    title,
                                    genre,
                                    plot,
                                    released,
                                    imdbRating,
                                    poster,
                                    poster
                                )

                            onFinished(Result.success(filme))
                        }

                    }

                }
            }
        })
    }

    override fun getCinemaJSON(onFinished: (Result<List<Cinema>>) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun inserirCinemas(cinemas: List<Cinema>, onFinished: () -> Unit) {
        TODO("Not yet implemented")
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

    override fun clearAll(onFinished: () -> Unit) {
        throw Exception("Illegal operation")
    }

    override fun inserirAvaliacao(filme: Filme, avaliacao: Avaliacao, onFinished: (Result<Filme>) -> Unit) {
        TODO("Not yet implemented")
    }

}
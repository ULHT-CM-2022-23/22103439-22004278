package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import java.text.SimpleDateFormat
import java.util.*

object App {

    val filme1 = Filme(
        "Ronaldo",
        "Documentary, Biography, Sport",
        "A close look at the life of Cristiano Ronaldo.",
        Calendar.getInstance().time,
        10,
        "https://www.imdb.com/video/vi997765913/?playlistId=tt5065822&ref_=tt_ov_vi",
        "img_ronaldo"
    )
    val filme2 = Filme(
        "Creed III",
        "Drama, Sport",
        "Adonis has been thriving in both his career and family life, but when a childhood friend and former boxing prodigy resurfaces, the face-off is more than just a fight.",
        Calendar.getInstance().time,
        7,
        "https://www.imdb.com/title/tt11145118/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_Creed%2520III",
        "img_creed_3"
    )

    val cinema1 = Cinema(
        cinema_id = 90829,
        cinema_name = "Fórum Montijo",
        cinema_provider = "Cinemas NOS",
        latitude = 38.6949792,
        longitude = -8.942057,
        address = "Praça da Liberdade, 32",
        postcode = "2870-461",
        county = "Montijo",
        ratings = listOf(
            Rating("limpeza de salas", 4),
            Rating("qualidade de som", 1),
            Rating("conforto", 10),
            Rating("qualidade de imagem", 9)
        ),
        opening_hours = mapOf(
            "Monday" to OpeningHours("14:00", "23:00"),
            "Tuesday" to OpeningHours("14:00", "23:00"),
            "Wednesday" to OpeningHours("14:00", "23:00"),
            "Thursday" to OpeningHours("14:00", "23:00"),
            "Friday" to OpeningHours("14:00", "23:00"),
            "Saturday" to OpeningHours("14:00", "23:00"),
            "Sunday" to OpeningHours("14:00", "23:00")
        )
    )

    val filmesVistos = mutableListOf<Review>(
        Review(
            "1",
            filme1,
            cinema1,
            10,
            Calendar.getInstance().time,
            listOf(),
            "FILME MUITO BOM!!"
        ),
        Review(
            "2",
            filme2,
            cinema1,
            7,
            Calendar.getInstance().time,
            listOf(),
            "FILME MUITO BOM!!"
        ),
    )


    val filmesIMDB = mutableListOf<Filme>(
        Filme(
            "Avatar",
            "Action, Adventure, Fantasy, Science Fiction",
            "In the 22nd century, there is a paraplegic Marine dispatched to the moon Pandora on a mission, however becomes protecting a alien culture and torn between following orders.",
            Calendar.getInstance().time,
            9,
            "https://www.imdb.com/title/tt1630029/?ref_=nv_sr_srsg_0_tt_7_nm_1_q_Avatar",
            "img_avatar"
        ),
        Filme(
            "Black Panther",
            "Action, Adventure, Science Fiction",
            "T'Challa, heir to the hidden but advanced kingdom of Wakanda, must step forward to lead his people into a new future and must confront a challenger from his country's past.",
            Calendar.getInstance().time,
            8,
            "https://www.imdb.com/title/tt1825683/?ref_=nv_sr_srsg_3_tt_6_nm_2_q_black%2520p",
            "img_blackpanther"
        ),
        filme1,
        filme2,
    )

    val cinemas = mutableListOf<Cinema>(
        cinema1
    )

    fun getFilmeById(uuid: String): Review? {
        return filmesVistos.find { it.id == uuid }
    }

    fun getFilmeByName(nomeFilme : String) : Filme? {
        for(filme in filmesIMDB){
            if (filme.nome == nomeFilme){
                return filme
            }
        }
        return null
    }

    fun getCinemaByName(nomeCinema : String) : Cinema? {
        for(cinema in cinemas){
            if (cinema.cinema_name == nomeCinema){
                return cinema
            }
        }
        return null
    }

    fun reviewExists(filme : Filme) : Boolean{
        for(review in filmesVistos){
            if (review.filme == filme){
                return true
            }
        }
        return false
    }

    fun listaOrdenadaPorAvaliacao() : List<Filme> {
        return filmesIMDB.sortedByDescending { it.avaliacao }
    }

    fun listaOrdenadaPorDataVisualizacao() : List<Review> {
        return filmesVistos.sortedByDescending { it.dataVisualizacao }
    }

    fun dataAbreviada(dataVisualizacao: Date):String {
        val format = SimpleDateFormat("dd/MM/yyyy")
        val components = format.format(dataVisualizacao).split("/")
        val day = components[0]
        val month = components[1]
        val year = components[2]
        return "$year/$month/$day"
    }

}

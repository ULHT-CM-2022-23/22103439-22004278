package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import java.io.File
import java.util.*

class Review (
    val id: String,
    val filme: Filme,
    val cinema: Cinema,
    val avalicao: Int,
    val dataVisualizacao: Date,
    val fotografiasRegistadas: List<File> = listOf(),
    val observacao: String?
)
{
    fun getImages(): List<File> {
        return fotografiasRegistadas
    }

}
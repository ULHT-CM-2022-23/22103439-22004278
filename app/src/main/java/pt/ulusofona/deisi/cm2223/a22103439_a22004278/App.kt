package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.*
import java.text.SimpleDateFormat
import java.util.*

object App {



    val filmesIMDB = mutableListOf<Avaliacao>()

    fun listaOrdenadaPorAvaliacao() : List<Avaliacao> {
        return filmesIMDB.sortedByDescending { it.avalicaoUtilizador }
    }

    fun listaOrdenadaPorDataVisualizacao() : List<Avaliacao> {
        return filmesIMDB.sortedByDescending { it.filmeIMDB.dataLancamentoIMDB }
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

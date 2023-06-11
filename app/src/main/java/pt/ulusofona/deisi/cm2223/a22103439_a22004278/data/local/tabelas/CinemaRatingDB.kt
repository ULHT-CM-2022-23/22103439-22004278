package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "cinema_ratings", primaryKeys = ["id_cinema", "categoria"])
data class CinemaRatingDB (
    @ColumnInfo(name = "id_cinema") val idCinema: Int,
    @ColumnInfo(name = "categoria") val categoria: String,
    @ColumnInfo(name = "score") val score: Int
)
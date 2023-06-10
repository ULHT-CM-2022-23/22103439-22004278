package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinemasbd")
data class CinemaDB(
    @PrimaryKey
    @ColumnInfo(name = "id_Cinema") val idCinema: Int,
    @ColumnInfo(name = "nome_Cinema") val nomeCinema: String,
)
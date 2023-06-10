package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cinemas")
data class CinemaDB(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "nome") val nome: String,
)
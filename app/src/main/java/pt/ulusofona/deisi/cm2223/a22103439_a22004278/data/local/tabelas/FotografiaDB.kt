package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fotografias")
data class FotografiaDB(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "fotografias_registadas") val fotografiasRegistadas: String?,
)
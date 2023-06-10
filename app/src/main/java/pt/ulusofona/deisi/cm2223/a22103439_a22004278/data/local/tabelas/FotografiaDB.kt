package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fotografiasbd")
data class FotografiaDB(
    @PrimaryKey
    @ColumnInfo(name = "id_fotografia") val idFotografia: Int,
    @ColumnInfo(name = "fotografia_registada") val fotografiasRegistadas: String?,
)
package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "avaliacao_fotos", primaryKeys = ["id_avaliacao", "uri_foto"])
class AvaliacaoFotoDB(
    @ColumnInfo(name = "id_avaliacao") val idAvaliacao: String,
    @ColumnInfo(name = "uri_foto") val uriFoto: String
)
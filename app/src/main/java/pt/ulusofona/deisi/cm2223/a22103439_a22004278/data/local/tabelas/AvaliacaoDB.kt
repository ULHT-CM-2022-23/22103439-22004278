package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avaliacoes")
data class AvaliacaoDB (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "avaliacao") val avaliacao: Int,
    @ColumnInfo(name = "data_visualizacao") val dataVisualizacao: Long,
    @ColumnInfo(name = "observacoes") val observacoes: String,
    val idFilme: String,
    val idCinema: Int
)

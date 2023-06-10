package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avaliacoesbd")
data class AvaliacaoDB (
    @PrimaryKey
    @ColumnInfo(name = "id_utilizador") val idUtilizador: String,
    @ColumnInfo(name = "avaliacao_utilizador") val avalicaoUtilizador: Int,
    @ColumnInfo(name = "data_visualizacao_utilizador") val dataVisualizacaoUtilizador: Long,
    @ColumnInfo(name = "observacao_utilizador") val observacaoUtilizador: String,
    val idFilme: String,
    val idCinema: Int
)

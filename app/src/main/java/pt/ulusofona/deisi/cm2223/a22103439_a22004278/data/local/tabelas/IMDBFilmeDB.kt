package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filmesbd")
data class IMDBFilmeDB (
    @PrimaryKey
    @ColumnInfo(name = "id_IMDB") val idIMDB: String,
    @ColumnInfo(name = "nome_IMDB") val nomeIMDB: String,
    @ColumnInfo(name = "genero_IMDB") val generoIMDB: String,
    @ColumnInfo(name = "sinopse_IMDB") val sinopseIMDB: String,
    @ColumnInfo(name = "data_lancamento_IMDB") val dataLancamentoIMDB: String,
    @ColumnInfo(name = "avaliacao_IMDB") val avaliacaoIMDB: String,
    @ColumnInfo(name = "link_IMDB") val linkIMDB: String,
    @ColumnInfo(name = "imagemCartaz_IMDB") val imagemCartazIMDB: String,
)
package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filmes")
data class FilmeDB (
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "genero") val genero: String,
    @ColumnInfo(name = "sinopse") val sinopse: String,
    @ColumnInfo(name = "data_lancamento") val dataLancamento: String,
    @ColumnInfo(name = "avaliacao") val avaliacao: String,
    @ColumnInfo(name = "link") val link: String,
    @ColumnInfo(name = "imagem_cartaz") val imagemCartaz: String,
    @ColumnInfo(name = "idiomas") val idiomas: String
)
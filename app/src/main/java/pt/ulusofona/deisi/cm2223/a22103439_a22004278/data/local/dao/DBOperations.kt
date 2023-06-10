package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.AvaliacaoDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.CinemaDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.FilmeDB

@Dao
interface DBOperations {
    @Query("SELECT * FROM filmes")
    fun getAllFlmes(): List<FilmeDB>

    @Query("SELECT * FROM filmes WHERE id = :id")
    fun getFilmeById(id: String): FilmeDB

    @Insert
    fun insertFilme(filme: FilmeDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllFilmes(filmes: List<FilmeDB>)

    @Query("DELETE FROM filmes")
    fun deleteAllFilmes()


    @Query("SELECT * FROM avaliacoes")
    fun getAllAvaliacoes(): List<AvaliacaoDB>

    @Query("SELECT * FROM avaliacoes WHERE id = :id")
    fun getAvaliacaoById(id: String): AvaliacaoDB

    @Insert
    fun insertAvaliacao(filme: AvaliacaoDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAvaliacoes(characters: List<AvaliacaoDB>)

    @Query("DELETE FROM avaliacoes")
    fun deleteAllAvaliacoes()


    @Query("SELECT * FROM cinemas")
    fun getAllCinemas(): List<CinemaDB>

    @Query("SELECT * FROM cinemas WHERE id = :id")
    fun getCinemaById(id: String): CinemaDB

    @Insert
    fun insertCinema(cinema: CinemaDB)
}
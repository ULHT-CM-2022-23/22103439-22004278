package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.AvaliacaoDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.CinemaDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.IMDBFilmeDB

@Dao
interface IMDBOperations {
    @Insert
    fun insert(filme: IMDBFilmeDB)

    @Query("SELECT * FROM filmesbd")
    fun getAll(): List<IMDBFilmeDB>

    @Query("SELECT * FROM filmesbd WHERE id_IMDB = :id")
    fun getById(id: String): IMDBFilmeDB

    @Query("DELETE FROM filmesbd")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(characters: List<IMDBFilmeDB>)


    @Insert
    fun insertAvaliacao(filme: AvaliacaoDB)

    @Query("SELECT * FROM avaliacoesbd")
    fun getAllAvaliacoes(): List<AvaliacaoDB>

    @Query("SELECT * FROM avaliacoesbd WHERE id_utilizador = :id")
    fun getByIdAvaliacao(id: String): AvaliacaoDB

    @Query("DELETE FROM avaliacoesbd")
    fun deleteAllAvaliacoes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAvaliacoes(characters: List<AvaliacaoDB>)


    @Insert
    fun insertCinema(cinema: CinemaDB)

    @Query("SELECT * FROM cinemasbd")
    fun getAllCinemas(): List<CinemaDB>

    @Query("SELECT * FROM cinemasbd WHERE id_Cinema = :id")
    fun getByIdCinema(id: String): CinemaDB
}
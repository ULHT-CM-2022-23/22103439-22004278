package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.*

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

    @Query("SELECT * FROM avaliacoes WHERE id_filme = :idFilme")
    fun getAvaliacaoByFilme(idFilme: String): AvaliacaoDB

    @Query("SELECT * FROM avaliacoes ORDER BY avaliacao DESC LIMIT 5")
    fun getTop5AvaliacoesDesc(): List<AvaliacaoDB>

    @Query("SELECT AVG(avaliacao) FROM avaliacoes")
    fun getMediaAvaliacoes(): Float

    @Query("SELECT COUNT(id) FROM avaliacoes")
    fun getCountAvaliacoes(): Int

    @Query("SELECT * FROM avaliacoes ORDER BY avaliacao ASC LIMIT 5")
    fun getTop5AvaliacoesAsc(): List<AvaliacaoDB>

    @Insert
    fun insertAvaliacao(filme: AvaliacaoDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAvaliacoes(avaliacoes: List<AvaliacaoDB>)

    @Insert
    fun insertFotoAvaliacao(foto: AvaliacaoFotoDB)

    @Query("SELECT * FROM avaliacao_fotos WHERE id_avaliacao = :idAvaliacao")
    fun getFotosAvaliacao(idAvaliacao: String): List<AvaliacaoFotoDB>

    @Query("DELETE FROM avaliacoes")
    fun deleteAllAvaliacoes()


    @Query("SELECT * FROM cinemas")
    fun getAllCinemas(): List<CinemaDB>

    @Query("SELECT * FROM cinemas WHERE id = :id")
    fun getCinemaById(id: Int): CinemaDB

    @Query("SELECT * FROM cinemas WHERE nome = :nome")
    fun getCinemaByNome(nome: String): CinemaDB

    @Query("SELECT * FROM cinema_ratings WHERE id_cinema = :id")
    fun getCinemaRatings(id: Int): List<CinemaRatingDB>

    @Insert
    fun insertCinema(cinema: CinemaDB)

    @Insert
    fun insertCinemaRating(rating: CinemaRatingDB)

    @Query("DELETE FROM cinemas")
    fun deleteAllCinemas()

    @Query("DELETE FROM cinema_ratings")
    fun deleteAllCinemaRatings()
}
package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.dao.DBOperations
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.*

@Database(entities = [
    FilmeDB::class,
    AvaliacaoDB::class,
    CinemaDB::class,
    CinemaRatingDB::class,
    FotografiaDB::class,
    AvaliacaoFotoDB::class], version = 2)
abstract class AppDatabase: RoomDatabase(){
    abstract fun operations(): DBOperations

    companion object {
        private var instance: AppDatabase? = null

        fun getInstance(applicationContext: Context): AppDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java,
                        "imdb_db"
                    ).build()
                }
                return instance as AppDatabase
            }
        }
    }
}
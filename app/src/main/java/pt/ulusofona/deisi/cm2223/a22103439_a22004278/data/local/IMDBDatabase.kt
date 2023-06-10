package pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.dao.IMDBOperations
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.AvaliacaoDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.CinemaDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.FotografiaDB
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.tabelas.IMDBFilmeDB

@Database(entities = [IMDBFilmeDB::class,AvaliacaoDB::class, CinemaDB::class, FotografiaDB::class], version = 1)
abstract class IMDBDatabase: RoomDatabase(){
    abstract fun imdbOperations(): IMDBOperations

    companion object {
        private var instance: IMDBDatabase? = null

        fun getInstance(applicationContext: Context): IMDBDatabase {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        applicationContext,
                        IMDBDatabase::class.java,
                        "imdb_db"
                    ).build()
                }
                return instance as IMDBDatabase
            }
        }
    }
}
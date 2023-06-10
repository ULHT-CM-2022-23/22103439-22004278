package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import android.app.Application
import android.util.Log
import okhttp3.OkHttpClient
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.IMDBRepository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.IMDBDatabase
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.IMDBRoom
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.remote.IMDBServiceWithOkHttpAndJSONObject

class IMDBApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        IMDBRepository.init(
            local = IMDBRoom(IMDBDatabase.getInstance(this).imdbOperations()),
            remote = IMDBServiceWithOkHttpAndJSONObject(client = OkHttpClient()),
            context = this
        )
        Log.i("APP", "Initialized repository")
    }
}
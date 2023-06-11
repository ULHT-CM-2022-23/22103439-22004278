package pt.ulusofona.deisi.cm2223.a22103439_a22004278

import android.app.Application
import android.util.Log
import okhttp3.OkHttpClient
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.Repository
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.AppDatabase
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.local.AppRoom
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.data.remote.APIServiceWithOkHttpAndJSONObject
import pt.ulusofona.deisi.cm2223.a22103439_a22004278.model.FusedLocation

class Application : Application(){
    override fun onCreate() {
        super.onCreate()
        Repository.init(
            local = AppRoom(AppDatabase.getInstance(this).operations()),
            remote = APIServiceWithOkHttpAndJSONObject(client = OkHttpClient()),
            context = this
        )
        FusedLocation.start(this)
        Log.i("APP", "Initialized repository")
    }
}
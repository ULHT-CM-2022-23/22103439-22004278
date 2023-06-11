package pt.ulusofona.deisi.cm2223.a22103439_a22004278.model

interface OnLocationChangedListener {

    // Aqui não vamos transferir um objeto Android, mas sim a latitude
    // e longitude para que a interface não dependa da framework Android
    fun onLocationChanged(latitude: Double, longitude: Double)
}
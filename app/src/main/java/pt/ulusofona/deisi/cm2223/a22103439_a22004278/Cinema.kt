package pt.ulusofona.deisi.cm2223.a22103439_a22004278

class Cinema(
    val cinema_id: Int,
    val cinema_name: String,
    val cinema_provider: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val postcode: String,
    val county: String,
    val ratings: List<Rating>,
    val opening_hours: Map<String, OpeningHours>
){

}
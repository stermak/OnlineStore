package youngdevs.production.onlinestore.data.entities

data class Landmark(
    val name: String,
    val location: Location
)

data class Location(
    val latitude: Double,
    val longitude: Double
)


package youngdevs.production.onlinestore.data.entities

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val availability: String,
    val image: String
)
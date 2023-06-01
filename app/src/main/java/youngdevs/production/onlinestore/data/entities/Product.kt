package youngdevs.production.onlinestore.data.entities

data class Product(
    val id: Long,
    val name: String,
    val description: String,
    val availability: String,
    val image: String
) {
    fun toCartItem(quantity: Int): CartItem {
        return CartItem(0, this.id, quantity, this.name, this.description, this.availability, this.image)
    }
}

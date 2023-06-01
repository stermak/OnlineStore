package youngdevs.production.onlinestore.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val cartItemId: Long,
    val productId: Long,
    val quantity: Int,
    val name: String,
    val description: String,
    val availability: String,
    val image: String
)

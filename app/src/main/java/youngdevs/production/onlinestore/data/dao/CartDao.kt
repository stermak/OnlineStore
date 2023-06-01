package youngdevs.production.onlinestore.data.dao

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import youngdevs.production.onlinestore.data.entities.CartItem

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAll(): LiveData<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem) {
        Log.d("CartDao", "Inserting item into cart: $cartItem")
        // Insert implementation...
    }

    @Delete
    suspend fun delete(cartItem: CartItem)

    @Query("DELETE FROM cart")
    suspend fun deleteAll()

    @Update
    suspend fun update(cartItem: CartItem)
}

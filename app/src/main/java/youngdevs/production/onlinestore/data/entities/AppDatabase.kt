package youngdevs.production.onlinestore.data.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import youngdevs.production.onlinestore.data.dao.CartDao

@Database(entities = [CartItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
}

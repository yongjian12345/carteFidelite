package cstjean.mobile.ecole.database
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
@Database(entities = [CarteFidelite::class], version = 1)
@TypeConverters(CarteFideliteTypeConverters::class)
abstract class CarteFideliteDatabase : RoomDatabase() {
    abstract fun carteFideliteDao(): CarteFideliteDao
}
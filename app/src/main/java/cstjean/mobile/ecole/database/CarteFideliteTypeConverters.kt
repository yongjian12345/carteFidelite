package cstjean.mobile.ecole.database
import androidx.room.TypeConverter
import cstjean.mobile.ecole.Commerce

class CarteFideliteTypeConverters {
    @TypeConverter
    fun fromCommerce(commerce: Commerce): String {
        return commerce.toString()
    }
    @TypeConverter
    fun toCommerce(milisSinceEpoch: String): Commerce {
        return Commerce.valueOf(milisSinceEpoch)
    }
}
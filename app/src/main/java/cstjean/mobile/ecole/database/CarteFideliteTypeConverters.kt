package cstjean.mobile.ecole.database
import androidx.room.TypeConverter
import cstjean.mobile.ecole.Commerce

/**
 * Convertisseur de type pour les cartes de fidélité.
 *
 * @author Raphael ostiguy & Yong Jian Qiu
 */
class CarteFideliteTypeConverters {

    /**
     * Convertit un [Commerce] en [String].
     *
     * @param commerce Le commerce à convertir.
     */
    @TypeConverter
    fun fromCommerce(commerce: Commerce): String {
        return commerce.toString()
    }

    /**
     * Convertit un [String] en [Commerce].
     *
     * @param milisSinceEpoch Le [String] à convertir.
     */
    @TypeConverter
    fun toCommerce(milisSinceEpoch: String): Commerce {
        return Commerce.valueOf(milisSinceEpoch)
    }
}
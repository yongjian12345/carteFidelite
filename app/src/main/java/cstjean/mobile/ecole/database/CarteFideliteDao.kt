package cstjean.mobile.ecole.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import androidx.room.Update

/**
 * DAO pour les cartes de fidélité.
 *
 * @author Raphael ostiguy & Yong Jian Qiu
 */
@Dao
interface CarteFideliteDao {
    @Query("SELECT * FROM carteFidelite")
    fun getCartesFidelites(): Flow<List<CarteFidelite>>
    @Query("SELECT * FROM cartefidelite WHERE id=(:id)")
    suspend fun getCarteFidelite(id: UUID): CarteFidelite

    @Insert
    suspend fun addCarteFidelite(carteFidelite: CarteFidelite)

    @Update
    suspend fun updateCarteFidelite(carteFidelite: CarteFidelite)

    @Delete
    suspend fun deleteCarteFidelite(carteFidelite: CarteFidelite)

}
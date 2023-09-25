package cstjean.mobile.ecole.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import kotlinx.coroutines.flow.Flow
import java.util.UUID
@Dao
interface CarteFideliteDao {
    @Query("SELECT * FROM carteFidelite")
    fun getTravaux(): Flow<List<CarteFidelite>>
    @Query("SELECT * FROM cartefidelite WHERE id=(:id)")
    suspend fun getTravail(id: UUID): CarteFidelite

    @Insert
    suspend fun addCarteFidelite(carteFidelite: CarteFidelite)
}
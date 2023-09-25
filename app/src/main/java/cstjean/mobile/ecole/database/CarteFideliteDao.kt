package cstjean.mobile.ecole.database
import androidx.room.Dao
import androidx.room.Query
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import java.util.UUID
@Dao
interface CarteFideliteDao {
    @Query("SELECT * FROM carteFidelite")
    fun getTravaux(): List<CarteFidelite>
    @Query("SELECT * FROM cartefidelite WHERE id=(:id)")
    fun getTravail(id: UUID): CarteFidelite
}
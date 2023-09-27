package cstjean.mobile.ecole
import android.content.Context
import androidx.room.Room
import cstjean.mobile.ecole.database.CarteFideliteDatabase
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "carteFidelite-database"

class CarteFideliteRepository @OptIn(DelicateCoroutinesApi::class)
private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope) {
    private val database: CarteFideliteDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CarteFideliteDatabase::class.java,
            DATABASE_NAME
        ).build()

    fun updateTravail(carteFidelite: CarteFidelite) {
        coroutineScope.launch {
            database.carteFideliteDao().updateCarteFidelite(carteFidelite)
        }
    }

    suspend fun deleteCarteFidelite(carteFidelite: CarteFidelite) {
        database.carteFideliteDao().deleteCarteFidelite(carteFidelite)
    }

    fun getCartesFidelites(): Flow<List<CarteFidelite>> = database.carteFideliteDao().getCartesFidelites()
    suspend fun getCarteFidelite(id: UUID): CarteFidelite = database.carteFideliteDao().getCarteFidelite(id)

    suspend fun addCarteFidelite(carteFidelite: CarteFidelite) {
        database.carteFideliteDao().addCarteFidelite(carteFidelite)
    }

    companion object {
        private var INSTANCE: CarteFideliteRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CarteFideliteRepository(context)
            }
        }
        fun get(): CarteFideliteRepository {
            return INSTANCE ?:
            throw IllegalStateException("CarteFideliteRepository doit être initialisé.")
        }
    }
}
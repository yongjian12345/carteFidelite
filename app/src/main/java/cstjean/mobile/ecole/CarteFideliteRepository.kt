package cstjean.mobile.ecole
import android.content.Context
import androidx.room.Room
import cstjean.mobile.ecole.database.CarteFideliteDatabase
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import java.util.UUID
private const val DATABASE_NAME = "carteFidelite-database"

class CarteFideliteRepository private constructor(context: Context) {
    private val database: CarteFideliteDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            CarteFideliteDatabase::class.java,
            DATABASE_NAME
        ).build()

    fun getTravaux(): List<CarteFidelite> = database.carteFideliteDao().getTravaux()
    fun getTravail(id: UUID): CarteFidelite = database.carteFideliteDao().getTravail(id)

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
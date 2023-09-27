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

/**
 * Le nom de la base de données.
 */
private const val DATABASE_NAME = "carteFidelite-database"


/**
 * Repository pour les cartes de fidélité.
 *
 * @property database La base de données.
 * @property coroutineScope Le scope pour les coroutines.
 */
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

    /**
     * Met à jour une carte de fidelité.
     *
     * @param carteFidelite La carte de fidelité à mettre à jour.
     */
    fun updateCarteFidelite(carteFidelite: CarteFidelite) {
        coroutineScope.launch {
            database.carteFideliteDao().updateCarteFidelite(carteFidelite)
        }
    }

    /**
     * Supprimé une carte de fidelité.
     *
     * @param carteFidelite La carte de fidelité à supprimer.
     */
    suspend fun deleteCarteFidelite(carteFidelite: CarteFidelite) {
        database.carteFideliteDao().deleteCarteFidelite(carteFidelite)
    }

    /**
     * Retourne la liste des cartes de fidelité.
     *
     * @return La liste des cartes de fidelité.
     */
    fun getCartesFidelites(): Flow<List<CarteFidelite>> = database.carteFideliteDao().getCartesFidelites()

    /**
     * Retourne une carte de fidelité.
     *
     * @param id Le ID de la carte de fidelité.
     * @return La carte de fidelité.
     */
    suspend fun getCarteFidelite(id: UUID): CarteFidelite = database.carteFideliteDao().getCarteFidelite(id)

    /**
     * Ajoute une carte de fidelité.
     *
     * @param carteFidelite La carte de fidelité à ajouter.
     */
    suspend fun addCarteFidelite(carteFidelite: CarteFidelite) {
        database.carteFideliteDao().addCarteFidelite(carteFidelite)
    }

    /**
     * Permet d'initialiser le repository.
     */
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
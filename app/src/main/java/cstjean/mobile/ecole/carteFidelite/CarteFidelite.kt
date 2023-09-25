package cstjean.mobile.ecole.carteFidelite

import androidx.room.Entity
import androidx.room.PrimaryKey
import cstjean.mobile.ecole.Commerce
import java.util.Date
import java.util.UUID

/**
 * Un travail scolaire.
 * as
 * @property id Le ID du travail.
 * @property nomCommerce Le nom du travail.
 * @property dateRemise La date de remise du travail.
 * @property estTermine Si le travail est terminé.
 *
 * @author Gabriel T. St-Hilaire
 */
@Entity
data class CarteFidelite(
    @PrimaryKey val id: UUID,
    val nomCommerce: String,
    val couleurBG: String,
    val typeCommerce: Commerce)

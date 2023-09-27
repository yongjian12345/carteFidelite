package cstjean.mobile.ecole.carteFidelite

import androidx.room.Entity
import androidx.room.PrimaryKey
import cstjean.mobile.ecole.Commerce
import cstjean.mobile.ecole.Couleur
import java.util.UUID

/**
 * Une carte fidelié.
 *
 * @property id Le ID du travail.
 * @property nomCommerce Le nom du commerce.
 * @property couleurBG La couleur de fond.
 * @property typeCommerce Le type de commerce.
 * @property numeroCarte Le numéro de la carte.
 *
 * @author Gabriel T. St-Hilaire
 */
@Entity
data class CarteFidelite(
    @PrimaryKey val id: UUID,
    val nomCommerce: String,
    val couleurBG: Couleur,
    val typeCommerce: Commerce,
    val numeroCarte: Int)

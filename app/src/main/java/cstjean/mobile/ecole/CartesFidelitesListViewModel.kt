package cstjean.mobile.ecole

import androidx.lifecycle.ViewModel
import cstjean.mobile.ecole.travail.CarteFidelite
import java.util.Date
import java.util.UUID

/**
 * ViewModel pour la liste des travaux.
 *
 * @property cartesFidelites La liste des travaux.
 *
 * @author Gabriel T. St-Hilaire
 */
class CartesFidelitesListViewModel : ViewModel() {
    val cartesFidelites = mutableListOf<CarteFidelite>()

    init {
        // Données de tests
        for (i in 0 until 100) {
            cartesFidelites += CarteFidelite(
                UUID.randomUUID(),
                "Travail #$i",
                Date(),
                i % 2 == 0
            )
        }
    }
}

package cstjean.mobile.ecole

import androidx.lifecycle.ViewModel
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
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
    private val cartesFidelitesRepository = CarteFideliteRepository.get()
    val cartesFidelites = cartesFidelitesRepository.getTravaux()

    /*init {

      // Données de tests
      for (i in 0 until 100) {
          cartesFidelites += CarteFidelite(
              UUID.randomUUID(),
              "Carte #$i",
              "Rouge",
              Commerce.RESTAURANT,
              3
          )
      }
    }*/
}

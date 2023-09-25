package cstjean.mobile.ecole

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import java.util.Date
import java.util.UUID

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
private const val TAG = "CartesFidelitesListViewModel"

/**
 * ViewModel pour la liste des travaux.
 *
 * @property cartesFidelites La liste des travaux.
 *
 * @author Gabriel T. St-Hilaire
 */
class CartesFidelitesListViewModel : ViewModel() {
    private val cartesFidelitesRepository = CarteFideliteRepository.get()
    init {
        viewModelScope.launch {

        }
    }


    suspend fun loadCartesFidelites(): List<CarteFidelite> {
// Données de tests
      /*  for (i in 0 until 100) {
            val carteFidelite = CarteFidelite(
                UUID.randomUUID(),
                "Carte #$i",
                "Rouge",
                Commerce.RESTAURANT,
                3
            )
            addCarteFidelite(carteFidelite)
        }*/
        return cartesFidelitesRepository.getTravaux()
    }

    suspend fun addCarteFidelite(carteFidelite: CarteFidelite) {
        cartesFidelitesRepository.addCarteFidelite(carteFidelite)
    }
}

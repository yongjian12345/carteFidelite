package cstjean.mobile.ecole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.ecole.carteFidelite.CarteFidelite

import kotlinx.coroutines.launch
import java.util.UUID

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
    val cartesFidelites = cartesFidelitesRepository.getCartesFidelites()
    init {
        viewModelScope.launch {
            loadCartesFidelites()
        }
    }


    suspend fun loadCartesFidelites() {
        for (i in 0 until 100) {
            val carteFidelite = CarteFidelite(
                UUID.randomUUID(),
                "Carte #$i",
                "Rouge",
                Commerce.RESTAURANT,
                3
            )
            addCarteFidelite(carteFidelite)
        }

    }

    suspend fun addCarteFidelite(carteFidelite: CarteFidelite) {
        cartesFidelitesRepository.addCarteFidelite(carteFidelite)
    }
}

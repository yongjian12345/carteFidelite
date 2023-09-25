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
    val cartesFidelites = mutableListOf<CarteFidelite>()
    init {
        viewModelScope.launch {
            Log.d(TAG, "Coroutine launched")
            cartesFidelites += loadCartesFidelites()
            Log.d(TAG, "Coroutine finished")
        }
    }


    suspend fun loadCartesFidelites(): List<CarteFidelite> {
        val cartesFidelites = mutableListOf<CarteFidelite>()
        delay(5000)
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
        return cartesFidelites
    }
}

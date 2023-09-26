package cstjean.mobile.ecole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.ecole.carteFidelite.CarteFidelite

import kotlinx.coroutines.launch
import java.util.UUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


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

    private val _cartesFidelites: MutableStateFlow<List<CarteFidelite>> = MutableStateFlow(emptyList())
    val cartesFidelites: StateFlow<List<CarteFidelite>> = _cartesFidelites
    init {
        viewModelScope.launch {
            loadCartesFidelites()

            cartesFidelitesRepository.getCartesFidelites().collect {
                _cartesFidelites.value = it
            }
        }
    }


    suspend fun loadCartesFidelites() {
/*
        for (i in 0 until 100) {
            val carteFidelite = CarteFidelite(
                UUID.randomUUID(),
                "Carte #$i",
                "Rouge",
                Commerce.RESTAURANT,
                3
            )
            addCarteFidelite(carteFidelite)
        }*/

    }

    suspend fun addCarteFidelite(carteFidelite: CarteFidelite) {
        cartesFidelitesRepository.addCarteFidelite(carteFidelite)
    }
}

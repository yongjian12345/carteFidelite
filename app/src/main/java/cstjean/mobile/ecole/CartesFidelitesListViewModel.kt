package cstjean.mobile.ecole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.ecole.carteFidelite.CarteFidelite

import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/**
 * ViewModel pour la liste des carte.
 *
 * @property cartesFidelites La liste des cartes.
 *
 * @author Raphael ostiguy & Yong Jian Qiu
 */
class CartesFidelitesListViewModel : ViewModel() {
    /**
     * Le repository pour les cartes de fidelité.
     */
    private val cartesFidelitesRepository = CarteFideliteRepository.get()

    /**
     * La liste des cartes de fidelité.
     */
    private val _cartesFidelites: MutableStateFlow<List<CarteFidelite>> = MutableStateFlow(emptyList())

    /**
     * La liste des cartes de fidelité.
     */
    val cartesFidelites: StateFlow<List<CarteFidelite>> = _cartesFidelites
    init {
        viewModelScope.launch {

            cartesFidelitesRepository.getCartesFidelites().collect {
                _cartesFidelites.value = it
            }
        }
    }

    /**
     * Permet d'ajouter une carte
     */
    suspend fun addCarteFidelite(carteFidelite: CarteFidelite) {
        cartesFidelitesRepository.addCarteFidelite(carteFidelite)
    }
}

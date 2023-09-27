package cstjean.mobile.ecole
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.update
import java.util.UUID

/**
 * ViewModel pour la liste des carte.
 *
 * @property cartesFidelites La liste des cartes.
 */
class CarteFideliteViewModel( carteFideliteId : UUID) : ViewModel() {

    /**
     * Le repository pour les cartes de fidelité.
     */
    private val carteFideliteRepository = CarteFideliteRepository.get()

    /**
     * La liste des cartes de fidelité.
     */
    private val _carteFidelite: MutableStateFlow<CarteFidelite?> = MutableStateFlow(null)

    /**
     * La liste des cartes de fidelité.
     */
    val carteFidelite: StateFlow<CarteFidelite?> = _carteFidelite

    init {
        viewModelScope.launch {
            _carteFidelite.value = carteFideliteRepository.getCarteFidelite(carteFideliteId)
        }
    }

    /**
     * Permet de modifier une carte
     */
    fun updateCarteFidelite(onUpdate: (CarteFidelite) -> CarteFidelite) {
        _carteFidelite.update { oldCarteFidelite ->
            oldCarteFidelite?.let { onUpdate(it) }
        }
    }


    /**
     * Permet d'enlever une carte
     */
    override fun onCleared() {
        super.onCleared()
        carteFidelite.value?.let { carteFideliteRepository.updateCarteFidelite(it) }
    }
}

/**
 * Factory pour le ViewModel.
 *
 * @property carteFideliteId L'identifiant de la carte de fidelité.
 */
class CarteFideliteViewModelFactory(private val carteFideliteId: UUID) : ViewModelProvider.Factory {

    /**
     * Création du ViewModel.
     *
     * @param modelClass La classe du ViewModel.
     * @return Le ViewModel.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CarteFideliteViewModel(carteFideliteId) as T
    }
}
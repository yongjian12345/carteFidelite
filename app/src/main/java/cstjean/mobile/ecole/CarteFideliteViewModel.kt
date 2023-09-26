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

class CarteFideliteViewModel( carteFideliteId : UUID) : ViewModel() {
    private val carteFideliteRepository = CarteFideliteRepository.get()
    private val _carteFidelite: MutableStateFlow<CarteFidelite?> = MutableStateFlow(null)
        val carteFidelite: StateFlow<CarteFidelite?> = _carteFidelite

    init {
        viewModelScope.launch {
            _carteFidelite.value = carteFideliteRepository.getCarteFidelite(carteFideliteId)
        }
    }

    fun updateTravail(onUpdate: (CarteFidelite) -> CarteFidelite) {
        _carteFidelite.update { oldCarteFidelite ->
            oldCarteFidelite?.let { onUpdate(it) }
        }
    }
}

class CarteFideliteViewModelFactory(private val carteFideliteId: UUID) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CarteFideliteViewModel(carteFideliteId) as T
    }
}
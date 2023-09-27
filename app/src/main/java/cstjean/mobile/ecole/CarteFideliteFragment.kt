package cstjean.mobile.ecole

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import cstjean.mobile.ecole.databinding.FragmentCarteFideliteBinding
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import kotlinx.coroutines.launch

/**
 * Fragment pour la gestion d'un travail ou écran modifier.
 *
 * @author Gabriel T. St-Hilaire
 */
class CarteFideliteFragment : Fragment() {
    private var _binding: FragmentCarteFideliteBinding? = null
    private val cartesFidelitesRepository = CarteFideliteRepository.get()

    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val args: CarteFideliteFragmentArgs by navArgs()
    private val carteFideliteViewModel: CarteFideliteViewModel by viewModels {
        CarteFideliteViewModelFactory(args.carteFideliteId)
    }
    // Supprimer la variable travail et le onCreate
    /**
     * Instanciation de l'interface.
     *
     * @param inflater Pour instancier l'interface.
     * @param container Le parent qui contiendra notre interface.
     * @param savedInstanceState Les données conservées au changement d'état.
     *
     * @return La vue instanciée.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCarteFideliteBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * Lorsque la vue est créée.
     *
     * @param view La vue créée.
     * @param savedInstanceState Les données conservées au changement d'état.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            // pas sur de ce que j'ai fait ici
            boutonDelete.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        carteFideliteViewModel.carteFidelite.collect { carteFidelite ->
                            if (carteFidelite != null) {
                                cartesFidelitesRepository.deleteCarteFidelite(carteFidelite)
                            }
                        }
                    }
                }
            }
            carteFideliteNumeroCarte.doOnTextChanged { text, _, _, _ ->
                carteFideliteViewModel.updateCarteFidelite { oldCarteFidelite ->
                    oldCarteFidelite.copy(numeroCarte = text.toString().toInt())
                }
            }
            carteFideliteNomCommerce.doOnTextChanged { text, _, _, _ ->
                carteFideliteViewModel.updateCarteFidelite { oldCarteFidelite ->
                    oldCarteFidelite.copy(nomCommerce = text.toString())
                }
            }
            carteFideliteCouleur.doOnTextChanged { text, _, _, _ ->
                carteFideliteViewModel.updateCarteFidelite { oldCarteFidelite ->
                    oldCarteFidelite.copy(couleurBG = text.toString())
                }
            }

            carteFideliteTypeCommerce.doOnTextChanged { text, _, _, _ ->
                carteFideliteViewModel.updateCarteFidelite { oldCarteFidelite ->
                    oldCarteFidelite.copy(typeCommerce =  Commerce.valueOf(text.toString()))
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                carteFideliteViewModel.carteFidelite.collect { carteFidelite ->
                    carteFidelite?.let { updateUi(it) }
                }
            }
        }
    }


    private fun updateUi( carteFidelite: CarteFidelite) {
        binding.apply {
            // To DO convenablement
            // Pour éviter une loop infinie avec le update
            if (carteFideliteNumeroCarte.text.toString() != carteFidelite.numeroCarte.toString()) {
                carteFideliteNumeroCarte.setText(carteFidelite.numeroCarte.toString())
            }
            carteFideliteNomCommerce.setText(carteFidelite.nomCommerce)
            carteFideliteCouleur.setText(carteFidelite.couleurBG)
            carteFideliteTypeCommerce.setText(carteFidelite.typeCommerce.toString())
        }
    }

    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
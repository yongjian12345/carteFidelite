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
            travailNom.doOnTextChanged { text, _, _, _ ->
                carteFideliteViewModel.updateTravail { oldTravail ->
                    oldTravail.copy(nom = text.toString())
                }
            }
/*
            travailTermine.setOnCheckedChangeListener { _, isChecked ->
               travailViewModel.updateTravail { oldTravail ->
 oldTravail.copy(estTermine = isChecked)
 }
            }*/

            // il ajoute les text
            travailDate.apply {

                isEnabled = false
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
    /*

    private fun updateUi( carteFidelite: CarteFidelite) {
        binding.apply {
            // To DO convenablement
            // Pour éviter une loop infinie avec le update
            if (travailNom.text.toString() != travail.nom) {
                travailNom.setText(travail.nom)
            }
            travailDate.text = travail.dateRemise.toString()
            travailTermine.isChecked = travail.estTermine
        }
    }
*/
    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
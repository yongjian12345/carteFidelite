package cstjean.mobile.ecole

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import cstjean.mobile.ecole.databinding.FragmentCartesFidelitesListBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import kotlinx.coroutines.launch
import java.util.UUID

private const val TAG = "TravauxListFragment"

/**
 * Fragment pour la liste des travaux.
 *
 * @author Gabriel T. St-Hilaire
 */
class CartesFidelitesListFragment : Fragment() {
    private var _binding: FragmentCartesFidelitesListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val cartesFidelitesListViewModel: CartesFidelitesListViewModel by viewModels()




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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartesFidelitesListBinding.inflate(inflater, container, false)

        binding.cartesFidelitesRecyclerView.layoutManager = LinearLayoutManager(context)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cartesFidelitesListViewModel.cartesFidelites.collect {cartesFidelite ->
                    binding.cartesFidelitesRecyclerView.adapter = CarteFideliteListAdapter(cartesFidelite) { cartefideliteId ->
                        Log.d(TAG, cartefideliteId.toString())
                        findNavController().navigate(CartesFidelitesListFragmentDirections.showCarteFideliteDetail(cartefideliteId))
                    }
                }
            }
        }
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_cartes_fidelites_list, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.nouveau_carte_fidelite -> {
                        viewLifecycleOwner.lifecycleScope.launch {
                            val nouveauCarteFidelite = CarteFidelite(
                                UUID.randomUUID(),
                                "george",
                                "roufge",
                                Commerce.RESTAURANT,
                                123,
                            )
                            cartesFidelitesListViewModel.addCarteFidelite(nouveauCarteFidelite)
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }



    /**
     * Lorsque la vue est détruite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

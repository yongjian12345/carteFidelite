package cstjean.mobile.ecole

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import cstjean.mobile.ecole.databinding.FragmentCarteFideliteBinding
import kotlinx.coroutines.launch


/**
 * Fragment pour la gestion d'un travail ou écran modifier.
 *
 * @author Gabriel T. St-Hilaire
 */
class CarteFideliteFragment : Fragment() {
    private var _binding: FragmentCarteFideliteBinding? = null
    private val cartesFidelitesRepository = CarteFideliteRepository.get()
    private lateinit var callback : OnBackPressedCallback
    private val binding
        get() = checkNotNull(_binding) {
            "Binding est null. La vue est visible ??"
        }

    private val args: CarteFideliteFragmentArgs by navArgs()
    private val carteFideliteViewModel: CarteFideliteViewModel by viewModels {
        CarteFideliteViewModelFactory(args.carteFideliteId)
    }
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
        val barcodeEncoder = BarcodeEncoder()
        callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            // Handle the back button event
        }


        binding.apply {


            val items = Commerce.values().map { it.toString() }.toTypedArray()
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

            binding.carteFideliteTypeCommerce.adapter = adapter

            binding.carteFideliteTypeCommerce.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedType = items[position]
                    carteFideliteViewModel.updateCarteFidelite { oldCarteFidelite ->
                        oldCarteFidelite.copy(typeCommerce = Commerce.valueOf(selectedType))
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

            val itemscouleur =  Couleur.values().map { it.toString() }.toTypedArray()

            val adaptercouleur = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, itemscouleur)
            binding.carteFideliteCouleur.adapter = adaptercouleur


            binding.carteFideliteCouleur.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val selectedType = itemscouleur[position]
                    carteFideliteViewModel.updateCarteFidelite { oldCarteFidelite ->
                        oldCarteFidelite.copy(couleurBG = Couleur.valueOf(selectedType))
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }


            boutonDelete.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        carteFideliteViewModel.carteFidelite.collect { carteFidelite ->
                            if (carteFidelite != null) {
                                cartesFidelitesRepository.deleteCarteFidelite(carteFidelite)
                            }
                            findNavController().navigate(
                                CarteFideliteFragmentDirections.showCarteFideliteList())

                        }

                    }
                }
            }


            carteFideliteNumeroCarte.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrBlank()) {
                    callback.isEnabled = true
                    carteFideliteQRCode.setImageBitmap(null)

                } else {
                    callback.isEnabled = false
                    carteFideliteQRCode.setImageBitmap(barcodeEncoder.encodeBitmap(text.toString(),BarcodeFormat.CODE_39, 800, 200))
                }
                carteFideliteQRCodeText.text = text.toString()
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    carteFideliteViewModel.carteFidelite.collect { carteFidelite ->
                        if (carteFidelite != null) {
                            carteFideliteQRCode.setImageBitmap(barcodeEncoder.encodeBitmap(carteFidelite.numeroCarte.toString(), BarcodeFormat.CODE_39, 800, 200))
                            carteFideliteQRCodeText.text = carteFidelite.numeroCarte.toString()
                        }

                    }
                }
            }

            carteFideliteNomCommerce.doOnTextChanged { text, _, _, _ ->
               callback.isEnabled = text.isNullOrBlank()
                carteFideliteViewModel.updateCarteFidelite { oldCarteFidelite ->
                    oldCarteFidelite.copy(nomCommerce = text.toString())
                }
            }
            /*carteFideliteCouleur.doOnTextChanged { text, _, _, _ ->
                carteFideliteViewModel.updateCarteFidelite { oldCarteFidelite ->
                    oldCarteFidelite.copy(couleurBG = text.toString())
                }
            }*/

            /*carteFideliteTypeCommerce.doOnTextChanged { text, _, _, _ ->
                carteFideliteViewModel.updateCarteFidelite { oldCarteFidelite ->
                    oldCarteFidelite.copy(typeCommerce =  Commerce.valueOf(text.toString()))
                }
            }*/


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

            // Pour éviter une loop infinie avec le update
            if (carteFideliteNumeroCarte.text.toString() != carteFidelite.numeroCarte.toString()) {
                carteFideliteNumeroCarte.setText(carteFidelite.numeroCarte.toString())
            }
            if (carteFideliteNomCommerce.text.toString() != carteFidelite.nomCommerce) {
                carteFideliteNomCommerce.setText(carteFidelite.nomCommerce)
            }


            val items = Commerce.values().map { it.toString() }.toTypedArray()
            val currentTypeCommerce = carteFidelite.typeCommerce.toString()

            // Trouvez l'index de cet élément dans votre tableau
            val defaultPosition = items.indexOf(currentTypeCommerce)

           // Définissez cet index comme élément sélectionné dans le Spinner
           carteFideliteTypeCommerce.setSelection(defaultPosition)

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
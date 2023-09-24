package cstjean.mobile.ecole

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import cstjean.mobile.ecole.databinding.ListItemCarteFideliteBinding
import cstjean.mobile.ecole.travail.CarteFidelite

/**
 * ViewHolder pour notre RecyclerView de travaux.
 *
 * @property binding Binding de la vue pour une cellule.
 *
 * @author Gabriel T. St-Hilaire
 */
class CarteFideliteHolder(private val binding: ListItemCarteFideliteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * On associe un travail à ce ViewHolder.
     *
     * @param carteFidelite Le travail associé.
     */
    fun bind(carteFidelite: CarteFidelite) {
        binding.travailNom.text = carteFidelite.nomCommerce
        binding.travailDate.text = carteFidelite.dateRemise.toString()
        binding.travailTermine.visibility = if (carteFidelite.estTermine) View.VISIBLE else View.GONE

        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context, carteFidelite.nomCommerce, Toast.LENGTH_SHORT)
                .show()
        }
    }
}

/**
 * Adapter pour notre RecyclerView de travaux.
 *
 * @property cartesFidelites Liste des travaux à afficher.
 *
 * @author Gabriel T. St-Hilaire
 */
class CarteFideliteListAdapter(private val cartesFidelites: List<CarteFidelite>) :
    RecyclerView.Adapter<CarteFideliteHolder>() {

    /**
     * Lors de la création des ViewHolder.
     *
     * @param parent Layout dans lequel la nouvelle vue
     *                 sera ajoutée quand elle sera liée à une position.
     * @param viewType Le type de vue de la nouvelle vue.
     *
     * @return Un ViewHolder pour notre cellule.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarteFideliteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCarteFideliteBinding.inflate(inflater, parent, false)
        return CarteFideliteHolder(binding)
    }

    /**
     * Associe un élément à un ViewHolder.
     *
     * @param holder Le ViewHolder à utiliser.
     * @param position La position dans la liste qu'on souhaite utiliser.
     */
    override fun onBindViewHolder(holder: CarteFideliteHolder, position: Int) {
        val carteFidelite = cartesFidelites[position]
        holder.bind(carteFidelite)
    }

    /**
     * Récupère le nombre total d'item de notre liste.
     *
     * @return Le nombre d'item total de notre liste.
     */
    override fun getItemCount() = cartesFidelites.size
}

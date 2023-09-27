package cstjean.mobile.ecole


import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cstjean.mobile.ecole.databinding.ListItemCarteFideliteBinding
import cstjean.mobile.ecole.carteFidelite.CarteFidelite
import java.util.UUID

/**
 * ViewHolder pour notre RecyclerView de cartes.
 *
 * @property binding Binding de la vue pour une cellule.
 *
 * @author Raphael ostiguy & Yong Jian Qiu
 */
class CarteFideliteHolder(private val binding: ListItemCarteFideliteBinding) :
    RecyclerView.ViewHolder(binding.root) {

    /**
     * On associe un travail à ce ViewHolder.
     *
     * @param carteFidelite Le travail associé.
     */
    fun bind(carteFidelite: CarteFidelite, onCarteFideliteClicked: (cartefideliteId: UUID) -> Unit) {
        binding.carteFideliteNomCommerce.text = carteFidelite.nomCommerce
        binding.carteFideliteNumero.text = carteFidelite.numeroCarte.toString()
        binding.carteFideliteType.text = carteFidelite.typeCommerce.toString()


        when (carteFidelite.typeCommerce) {
            Commerce.EPICERIE -> binding.carteFideliteIcon.setImageResource(R.drawable.baseline_local_grocery_store_24)
            Commerce.RESTAURANT-> binding.carteFideliteIcon.setImageResource(R.drawable.baseline_restaurant_24)
            Commerce.DIVERTISSEMENT -> binding.carteFideliteIcon.setImageResource(R.drawable.baseline_color_lens_24)
            Commerce.AUTRE -> binding.carteFideliteIcon.setImageResource(R.drawable.baseline_shopping_basket_24)
        }

        val colorRes = ContextCompat.getColor(binding.root.context, carteFidelite.couleurBG.colorResId)
        binding.root.updateRoundedBackgroundWithColor(colorRes)

        binding.root.setOnClickListener {
            onCarteFideliteClicked(carteFidelite.id)
        }
    }
    private fun View.updateRoundedBackgroundWithColor(color: Int) {
        val backgroundDrawable = this.background as? GradientDrawable
        backgroundDrawable?.setColor(color)
    }

}

/**
 * Adapter pour notre RecyclerView de cartes.
 *
 * @property cartesFidelites Liste des cartes à afficher.
 *
 * @author Raphael ostiguy & Yong Jian Qiu
 */
class CarteFideliteListAdapter(
    private val cartesFidelites: List<CarteFidelite>,
    private val onCarteFideliteClicked: (cartefideliteId: UUID) -> Unit
) :
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
        holder.bind(carteFidelite, onCarteFideliteClicked)
    }

    /**
     * Récupère le nombre total d'item de notre liste.
     *
     * @return Le nombre d'item total de notre liste.
     */
    override fun getItemCount() = cartesFidelites.size
}

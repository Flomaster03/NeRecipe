package ru.netology.recipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.recipes.R
import ru.netology.recipes.databinding.SingleRecipeBinding
import ru.netology.recipes.dto.Recipe

class RecipeAdapter(
    val interactionListener: RecipeInteractionListener
) :
    ListAdapter<Recipe, RecipeAdapter.ViewHolder>(diffCallback) {

    class ViewHolder(
        private val binding: SingleRecipeBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.menuOptions).apply {
                inflate(R.menu.options_menu)
                setOnMenuItemClickListener { MenuItem ->
                    when (MenuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.title.setOnClickListener { listener.onSingleRecipeClicked(recipe) }
            binding.authorName.setOnClickListener { listener.onSingleRecipeClicked(recipe) }
            binding.categoryRecipe.setOnClickListener { listener.onSingleRecipeClicked(recipe) }
            binding.textRecipe.setOnClickListener { listener.onSingleRecipeClicked(recipe) }

            binding.menuOptions.setOnClickListener { popupMenu.show() }
            binding.buttonFavourite.setOnClickListener { listener.onFavouriteClicked(recipe.id) }
        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                title.text = recipe.title
                authorName.text = recipe.authorName
                categoryRecipe.text = recipe.categoryRecipe
                textRecipe.text = recipe.textRecipe
                buttonFavourite.isChecked = recipe.isFavourite
            }
        }
    }


    private object diffCallback : DiffUtil.ItemCallback<Recipe>() {

        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SingleRecipeBinding.inflate(
            inflater,
            parent, false
        )
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }


}
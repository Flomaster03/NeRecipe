package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.R
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.databinding.FragmentFavouriteBinding
import ru.netology.recipes.ui.UpdateFragment.Companion.authorNameArg
import ru.netology.recipes.ui.UpdateFragment.Companion.categoryArg
import ru.netology.recipes.ui.UpdateFragment.Companion.idArgs
import ru.netology.recipes.ui.UpdateFragment.Companion.textArg
import ru.netology.recipes.ui.UpdateFragment.Companion.titleArg
import ru.netology.recipes.viewModel.RecipeViewModel

class FavouriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavouriteBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipeAdapter(viewModel)

        binding.listFavourite.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)

            val favRecipes = recipes.none { it.isFavourite }
            if (favRecipes) {
                binding.emptyFavouriteText.isVisible = favRecipes
                binding.emptyFavouriteImage.isVisible = favRecipes
            }
            viewModel.data.observe(viewLifecycleOwner) { recipes ->
                val listFavRecipes = recipes.filter { it.isFavourite }
                adapter.submitList(listFavRecipes)
            }

        }
        viewModel.toUpdateFragment.observe(viewLifecycleOwner) {
            val updatedRecipe = viewModel.updateRecipe.value
            findNavController().navigate(
                R.id.action_favouriteFragment_to_updateFragment,
                if (updatedRecipe != null) { Bundle().apply {
                    idArgs = updatedRecipe.id
                    titleArg = updatedRecipe.title
                    authorNameArg = updatedRecipe.authorName
                    categoryArg = updatedRecipe.categoryRecipe
                    textArg = updatedRecipe.textRecipe
                }
                } else return@observe
            )
        }


        viewModel.toSingleFragment.observe(viewLifecycleOwner) { id ->
            findNavController().navigate(
                R.id.action_favouriteFragment_to_viewSingleFragment,
                Bundle().apply { idArgs = id }
            )
        }

        return binding.root
    }
}
package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.R
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.databinding.FragmentFavouriteBinding
import ru.netology.recipes.databinding.FragmentFeedBinding
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
                val favRecipes = recipes.filter { it.isFavourite }
                adapter.submitList(favRecipes)
            }

        }
        viewModel.toUpdateFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_favouriteFragment_to_updateFragment)
            findNavController().navigateUp()
        }

        viewModel.toSingleFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_favouriteFragment_to_viewSingleFragment)
            findNavController().navigateUp()
        }
        return binding.root
    }
}
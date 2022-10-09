package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.R
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.databinding.FragmentFavouriteBinding
import ru.netology.recipes.viewModel.RecipeViewModel

class FavouriteFragment: Fragment() {
    private val viewModel by activityViewModels<RecipeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFavouriteBinding.inflate(layoutInflater, container, false).also { binding ->

        viewModel.data.observe(viewLifecycleOwner) { recipes ->

            val favRecipes = recipes.none { it.isFavourite }
            if (favRecipes) {
                binding.emptyFavouriteText.isVisible = favRecipes
                binding.emptyFavouriteImage.isVisible = favRecipes
            }
        }


        val adapter = RecipeAdapter(viewModel)
        binding.listFavourite.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val favRecipes = recipes.filter { it.isFavourite }
            adapter.submitList(favRecipes)
        }

        //      binding.bottomToolbar.setOnItemSelectedListener {
        //          when (it.itemId) {
        //              R.id.feed -> findNavController().popBackStack()
        //          }
        //          true
        //      }
        //      binding.bottomToolbar.setOnItemSelectedListener { menuItem ->
        //          when (menuItem.itemId) {
        //              R.id.favourites -> {
        //                  viewModel.favoriteFragment.call()
        //                  true
        //              }
        //              R.id.filter -> {
        //                  viewModel.filterFragment.call()
        //                  true
        //              }
        //              R.id.feed -> {
        //                  viewModel.feedFragment.observe(viewLifecycleOwner) {
        //                      val directions = RecipeFavoriteFragmentDirections.actionFavoriteFragmentToFeedFragment()
        //                      findNavController().navigate(directions)
        //                  }
        //                  true
        //              }
        //              else -> false
        //          }
        //      }
    }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.toUpdateFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_favouriteFragment_to_updateFragment)
            findNavController().navigateUp()
        }

        viewModel.toSingleFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_favouriteFragment_to_viewSingleFragment)
            findNavController().navigateUp()
        }


        //     viewModel.filterFragment.observe(this) {
        //         val directions = FeedRecipeFragmentDirections.recipeFilterFragment()
        //         findNavController().navigate(directions)
        //     }
    }
}
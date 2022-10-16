package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.R
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.databinding.FragmentFeedBinding
import ru.netology.recipes.ui.UpdateFragment.Companion.authorNameArg
import ru.netology.recipes.ui.UpdateFragment.Companion.categoryArg
import ru.netology.recipes.ui.UpdateFragment.Companion.idArgs
import ru.netology.recipes.ui.UpdateFragment.Companion.textArg
import ru.netology.recipes.ui.UpdateFragment.Companion.titleArg
import ru.netology.recipes.viewModel.RecipeViewModel

class FeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipeAdapter(viewModel)

        binding.recipeRecyclerView.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }

        viewModel.data.observe(viewLifecycleOwner) {
            if (viewModel.data.value.isNullOrEmpty()) {
                binding.emptyFilterGroup.visibility = VISIBLE
            } else {
                binding.emptyFilterGroup.visibility = GONE
            }
        }

        binding.addRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createFragment)
        }


        viewModel.toFavoriteFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_feedFragment_to_favouriteFragment
            )
        }


        viewModel.toUpdateFragment.observe(viewLifecycleOwner) {
            val updatedRecipe = viewModel.updateRecipe.value
            findNavController().navigate(
                R.id.action_feedFragment_to_updateFragment,
                if (updatedRecipe != null) {
                    Bundle().apply {
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
                R.id.action_feedFragment_to_viewSingleFragment,
                Bundle().apply { idArgs = id }
            )
        }


        viewModel.toFilterFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_feedFragment_to_filterFragment
            )
        }


        binding.searchView.setOnClickListener {
            findNavController().navigate(
                R.id.action_feedFragment_to_searchFragment
            )
        }
        return binding.root
    }
}

package ru.netology.recipes.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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



        binding.addRecipe.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_createFragment)
        }

//       viewModel.toCreateFragment.observe(viewLifecycleOwner) {
//           println("toFragment")
//           findNavController().navigate(R.id.action_feedFragment_to_createFragment)
//       }


        viewModel.toFavoriteFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_feedFragment_to_favouriteFragment
            )
        }


        viewModel.toUpdateFragment.observe(viewLifecycleOwner) {
            val updatedRecipe = viewModel.updateRecipe.value
            findNavController().navigate(
                R.id.action_feedFragment_to_updateFragment,
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
                R.id.action_feedFragment_to_viewSingleFragment,
                Bundle().apply { idArgs = id }
            )
        }


        viewModel.toFilterFragment.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_feedFragment_to_filterFragment
            )
        }

        fun viewRecipe() {
            viewModel.data.observe(viewLifecycleOwner) { recipe ->
                adapter.submitList(recipe)
            }
        }

 /*       viewRecipe()
        if (viewModel.filterIsActive) {
            binding.buttonClearFilter.isVisible = viewModel.filterIsActive
            binding.buttonClearFilter.setOnClickListener {
                viewModel.clearFilter()
                viewModel.filterIsActive = false
                binding.buttonClearFilter.visibility = View.GONE
                viewRecipe()
                viewModel.toggleCheckEuropean = true
                viewModel.toggleCheckPanasian = true
                viewModel.toggleCheckAmerican = true
                viewModel.toggleCheckEastern = true
                viewModel.toggleCheckMediterranean = true
                viewModel.toggleCheckRussian = true
                viewModel.toggleCheckAsian = true
            }
        } else {
            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isNotBlank()) {
                        viewModel.onSearchClicked(newText)
                        viewRecipe()
                    }
                    if (TextUtils.isEmpty(newText)) {
                        viewModel.clearFilter()
                        viewRecipe()
                    }
                    return false
                }
            }
            )
        }

        binding.addRecipe.setOnClickListener {
            viewModel.onCreateClicked()
        }*/
        return binding.root
    }
}

package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.R
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.databinding.FragmentFilterListBinding
import ru.netology.recipes.ui.UpdateFragment.Companion.authorNameArg
import ru.netology.recipes.ui.UpdateFragment.Companion.categoryArg
import ru.netology.recipes.ui.UpdateFragment.Companion.idArgs
import ru.netology.recipes.ui.UpdateFragment.Companion.textArg
import ru.netology.recipes.ui.UpdateFragment.Companion.titleArg
import ru.netology.recipes.viewModel.RecipeViewModel

class FilterListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFilterListBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipeAdapter(viewModel)

        binding.listFilter.adapter = adapter

        if (viewModel.data.value.isNullOrEmpty()) {
            findNavController().navigateUp()
        }

//      { Snackbar.make(binding.root, "Список рецептов пуст!", Snackbar.LENGTH_INDEFINITE)
//              .setAction("Return to the Recipes") {
//                  findNavController().navigateUp()
//              }
//              .show()
//      }

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val showFilteredList = recipes.filter {
                viewModel.filteredList.contains(it.categoryRecipe)
            }
            if (showFilteredList.isNotEmpty()) {
                adapter.submitList(showFilteredList)
            } else {
                Toast.makeText(activity, "Ничего не найдено", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigateUp()
            }

        }

        binding.buttonToFilter.setOnClickListener {
            viewModel.filteredList.clear()
            findNavController().navigateUp()
        }

        binding.buttonToRecipes.setOnClickListener {
            viewModel.filteredList.clear()
            findNavController().navigate(
                R.id.action_filterListFragment_to_feedFragment
            )
        }

        viewModel.toUpdateFragment.observe(viewLifecycleOwner) {
            val updatedRecipe = viewModel.updateRecipe.value
            findNavController().navigate(
                R.id.action_filterListFragment_to_updateFragment,
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
                R.id.action_filterListFragment_to_viewSingleFragment,
                Bundle().apply { idArgs = id }
            )
        }
        return binding.root
    }
}



package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.R
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.databinding.FragmentFilterListBinding
import ru.netology.recipes.dto.Recipe
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

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }

        //binding.emptyFilterText.isInvisible
       // binding.emptyFilterImage.isInvisible

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val showFilteredList = recipes.filter {
                viewModel.filteredList.contains(it.categoryRecipe)
            }
            if (showFilteredList.isNotEmpty()) {
                adapter.submitList(showFilteredList)
                findNavController().navigateUp()
            } else {
                Toast.makeText(activity, "Ничего не найдено", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigateUp()
            }

        }

        //      fun find (resipes: List<Recipe>?, categories: MutableList<String>?): MutableList<Recipe>? {
        //          val showFilteredList: MutableList<Recipe>? = null
        //          if (categories != null) {
        //              for (category in categories) {
        //                  if (resipes != null) {
        //                      for (recipe in resipes) {
        //                          if (recipe.categoryRecipe == category) {
        //                              showFilteredList?.add(recipe)
        //                          }
        //                      }
        //                  }
        //              }
        //          }
        //          return showFilteredList
        //       }

        //      viewModel.data.observe(viewLifecycleOwner) { recipes ->
        //          val dataList = viewModel.data.value
        //          val categoriesList = viewModel.filteredList.value
        //          val showFilteredList = find(dataList, categoriesList)?.toList()
        //          if (showFilteredList != null) {
        //              adapter.submitList(showFilteredList)
        //          } else {
        //
        //          }
        //      }
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
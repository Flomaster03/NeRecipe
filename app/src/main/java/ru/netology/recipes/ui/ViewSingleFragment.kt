package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.util.LongArgs
import ru.netology.nmedia.util.StringArgs
import ru.netology.recipes.R
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.databinding.FragmentViewSingleBinding
import ru.netology.recipes.viewModel.RecipeViewModel

class ViewSingleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentViewSingleBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val viewHolder = RecipeAdapter.ViewHolder(binding.singleRecipeLayout, viewModel)


        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val singleRecipe = recipes.find { it.id == arguments?.idArgs } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            viewHolder.bind(singleRecipe)
        }


        viewModel.toUpdateFragment.observe(viewLifecycleOwner) {
            val updatedRecipe = viewModel.updateRecipe.value
            findNavController().navigate(
                R.id.action_viewSingleFragment_to_updateFragment,
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
        return binding.root
    }

    companion object {
        var Bundle.idArgs: Long by LongArgs
        var Bundle.titleArg: String? by StringArgs
        var Bundle.authorNameArg: String? by StringArgs
        var Bundle.categoryArg: String? by StringArgs
        var Bundle.textArg: String? by StringArgs
    }

}

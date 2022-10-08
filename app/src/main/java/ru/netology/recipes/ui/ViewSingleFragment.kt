package ru.netology.nerecipe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nerecipe.adapter.RecipeAdapter
import ru.netology.nmedia.util.LongArgs
import ru.netology.nmedia.util.StringArgs
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
//    private val args by navArgs<toRecipeViewFragmentArgs>()
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ) = FragmentViewOfSingleRecipeBinding.inflate(layoutInflater, container, false).also { binding ->
//        incomingArg(binding)
//        binding.bottomToolbar.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.feed -> findNavController().popBackStack()
//            }
//            true
//        }
//    }.root
//
//    private fun incomingArg (binding: FragmentViewOfSingleRecipeBinding) {
//        binding.title.text = args.viewRecipe?.title.toString()
//        binding.authorName.text = args.viewRecipe?.authorName.toString()
//        binding.categoryRecipe.text = args.viewRecipe?.categoryRecipe.toString()
//        binding.textRecipe.text = args.viewRecipe?.textRecipe.toString()
//    }
//}

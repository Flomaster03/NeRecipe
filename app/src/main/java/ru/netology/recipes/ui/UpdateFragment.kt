package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.util.LongArgs
import ru.netology.nmedia.util.StringArgs
import ru.netology.recipes.R
import ru.netology.recipes.databinding.FragmentFavouriteBinding
import ru.netology.recipes.databinding.FragmentUpdateBinding
import ru.netology.recipes.dto.Category
import ru.netology.recipes.dto.Recipe
import ru.netology.recipes.viewModel.RecipeViewModel

class UpdateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        binding.checkBoxEuropean.text = binding.checkBoxEuropean.context.showCategories(Category.European)
        binding.checkBoxAsian.text = binding.checkBoxAsian.context.showCategories(Category.Asian)
        binding.checkBoxPanasian.text = binding.checkBoxPanasian.context.showCategories(Category.PanAsian)
        binding.checkBoxEastern.text = binding.checkBoxEastern.context.showCategories(Category.Eastern)
        binding.checkBoxAmerican.text = binding.checkBoxAmerican.context.showCategories(Category.American)
        binding.checkBoxRussian.text = binding.checkBoxRussian.context.showCategories(Category.Russian)
        binding.checkBoxMediterranean.text =
            binding.checkBoxMediterranean.context.showCategories(Category.Mediterranean)

        val title = arguments?.titleArg
        val authorName = arguments?.authorNameArg
        val categoryRecipe = arguments?.textArg
        val textRecipe = arguments?.textArg

        title?.let { binding.title.setText(it) }
        authorName?.let { binding.authorName.setText(it) }
        categoryRecipe?.let { binding.categoryRecipe.setText(it) }
        textRecipe?.let { binding.textRecipe.setText(it) }



            binding.title.requestFocus()
            binding.buttonSave.setOnClickListener {
                if (!binding.title.text.isNullOrBlank()
                    && !binding.authorName.text.isNullOrBlank()
                    && !binding.categoryRecipe.text.isNullOrBlank()
                    && !binding.textRecipe.text.isNullOrBlank()
                        ) {
                        viewModel.onSaveButtonClicked(
                            title = binding.title.text.toString(),
                            authorName = binding.authorName.text.toString(),
                            categoryRecipe = getCheckedCategory(binding.categoryRecipeCheckBox.checkedRadioButtonId),
                            textRecipe = binding.textRecipe.text.toString()
                        )
                } else {
                    Toast.makeText(activity, "Все поля должны быть заполнены", Toast.LENGTH_LONG)
                        .show()
                }
                findNavController().navigateUp()
            }
        return binding.root
    }

    private fun getCheckedCategory(checkedId: Int) = when (checkedId) {
        R.id.checkBoxEuropean -> Category.European
        R.id.checkBoxAsian -> Category.Asian
        R.id.checkBoxPanasian -> Category.PanAsian
        R.id.checkBoxEastern -> Category.Eastern
        R.id.checkBoxAmerican -> Category.American
        R.id.checkBoxRussian -> Category.Russian
        R.id.checkBoxMediterranean -> Category.Mediterranean
        else -> throw IllegalArgumentException("Unknown type: $checkedId")
    }

    companion object {
        var Bundle.idArgs: Long by LongArgs
        var Bundle.titleArg: String? by StringArgs
        var Bundle.authorNameArg: String? by StringArgs
        var Bundle.categoryArg: String? by StringArgs
        var Bundle.textArg: String? by StringArgs
    }
}













}
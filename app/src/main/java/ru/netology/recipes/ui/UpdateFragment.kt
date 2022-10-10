package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.util.LongArgs
import ru.netology.nmedia.util.StringArgs
import ru.netology.recipes.R
import ru.netology.recipes.databinding.FragmentUpdateBinding
import ru.netology.recipes.viewModel.RecipeViewModel

class UpdateFragment : Fragment() {

    private var categoryRecipeNumber = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val title = arguments?.titleArg
        val authorName = arguments?.authorNameArg
        val categoryRecipe = arguments?.textArg
        val textRecipe = arguments?.textArg

        binding.categoryRecipeCheckBox.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.checkBoxEuropean -> categoryRecipeNumber = binding.checkBoxEuropean.text.toString()
                R.id.checkBoxAsian -> categoryRecipeNumber = binding.checkBoxAsian.text.toString()
                R.id.checkBoxPanasian -> categoryRecipeNumber = binding.checkBoxPanasian.text.toString()
                R.id.checkBoxEastern -> categoryRecipeNumber = binding.checkBoxEastern.text.toString()
                R.id.checkBoxAmerican -> categoryRecipeNumber = binding.checkBoxAmerican.text.toString()
                R.id.checkBoxRussian -> categoryRecipeNumber = binding.checkBoxRussian.text.toString()
                R.id.checkBoxMediterranean -> categoryRecipeNumber = binding.checkBoxMediterranean.text.toString()

            }
        }

        title?.let { binding.title.setText(it) }
        authorName?.let { binding.authorName.setText(it) }
        textRecipe?.let { binding.textRecipe.setText(it) }

            binding.title.requestFocus()
            binding.buttonSave.setOnClickListener {
                if (!binding.title.text.isNullOrBlank()
                    && !binding.authorName.text.isNullOrBlank()
                    && !categoryRecipe.isNullOrBlank()
                    && !binding.textRecipe.text.isNullOrBlank()
                        ) {
                        viewModel.onSaveButtonClicked(
                            title = binding.title.text.toString(),
                            authorName = binding.authorName.text.toString(),
                            categoryRecipe = categoryRecipeNumber,
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

    companion object {
        var Bundle.idArgs: Long by LongArgs
        var Bundle.titleArg: String? by StringArgs
        var Bundle.authorNameArg: String? by StringArgs
        var Bundle.categoryArg: String? by StringArgs
        var Bundle.textArg: String? by StringArgs
    }
}














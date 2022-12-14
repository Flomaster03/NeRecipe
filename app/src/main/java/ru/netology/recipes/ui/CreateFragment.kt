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
import ru.netology.recipes.databinding.FragmentCreateBinding
import ru.netology.recipes.viewModel.RecipeViewModel

class CreateFragment : Fragment() {

    private var categoryRecipeNumber = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentCreateBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)


        binding.categoryRecipeCheckBox.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.checkBoxEuropean -> categoryRecipeNumber =
                    binding.checkBoxEuropean.text.toString()
                R.id.checkBoxAsian -> categoryRecipeNumber = binding.checkBoxAsian.text.toString()
                R.id.checkBoxPanasian -> categoryRecipeNumber =
                    binding.checkBoxPanasian.text.toString()
                R.id.checkBoxEastern -> categoryRecipeNumber =
                    binding.checkBoxEastern.text.toString()
                R.id.checkBoxAmerican -> categoryRecipeNumber =
                    binding.checkBoxAmerican.text.toString()
                R.id.checkBoxRussian -> categoryRecipeNumber =
                    binding.checkBoxRussian.text.toString()
                R.id.checkBoxMediterranean -> categoryRecipeNumber =
                    binding.checkBoxMediterranean.text.toString()
            }
        }

        binding.title.requestFocus()
        binding.buttonSave.setOnClickListener {
            if (!binding.title.text.isNullOrBlank()
                && !binding.authorName.text.isNullOrBlank()
                && !categoryRecipeNumber.isBlank()
                && !binding.textRecipe.text.isNullOrBlank()
            ) {
                viewModel.onSaveButtonClicked(
                    title = binding.title.text.toString(),
                    authorName = binding.authorName.text.toString(),
                    categoryRecipe = categoryRecipeNumber,
                    textRecipe = binding.textRecipe.text.toString()
                )
            } else {
                Toast.makeText(activity, "?????? ???????? ???????????? ???????? ??????????????????", Toast.LENGTH_LONG)
                    .show()
            }
            findNavController().navigateUp()
        }
        return binding.root
    }
}

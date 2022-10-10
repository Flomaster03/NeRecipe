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
import ru.netology.recipes.R
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.databinding.FragmentCreateBinding
import ru.netology.recipes.databinding.FragmentFavouriteBinding
import ru.netology.recipes.viewModel.RecipeViewModel

class CreateFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCreateBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipeAdapter(viewModel)

        binding.scrollContent.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)


    private val viewModel by activityViewModels<RecipeViewModel>()
    private var categoryRecipeNumber = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCreateBinding.inflate(layoutInflater, container, false).also { binding ->

        binding.buttonSave.setOnClickListener {
            onSaveButtonClicked(binding)
        }

        binding.categoryRecipeCheckBox.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.checkBoxEuropean -> categoryRecipeNumber = "Европейская кухня"
                R.id.checkBoxAsian -> categoryRecipeNumber = "Азиатская кухня"
                R.id.checkBoxPanasian -> categoryRecipeNumber = "Паназиатская кухня"
                R.id.checkBoxEastern -> categoryRecipeNumber = "Восточная кухня"
                R.id.checkBoxAmerican -> categoryRecipeNumber = "Американская кухня"
                R.id.checkBoxRussian -> categoryRecipeNumber = "Русская кухня"
                R.id.checkBoxMediterranean -> categoryRecipeNumber = "Средиземноморская кухня"
            }
        }

        binding.buttonSave.setOnClickListener {
            onSaveButtonClicked(binding)
        }
    }.root

    private fun onSaveButtonClicked(binding: FragmentCreateBinding) {

        val title = binding.title.text.toString()
        val authorName = binding.authorName.text.toString()
        val textRecipe = binding.textRecipe.text.toString()

        if (!emptyCheckUpdateWarning(title = title, authorName = authorName, textRecipe = textRecipe, categoryRecipe = categoryRecipeNumber)) return

        viewModel.onSaveClicked(title = title, authorName = authorName, categoryRecipe = categoryRecipeNumber, textRecipe = textRecipe)
        findNavController().popBackStack()
    }

    private fun emptyCheckUpdateWarning(
        title: String,
        authorName: String,
        textRecipe: String,
        categoryRecipe: String
    ): Boolean {
        return if (title.isBlank() || authorName.isBlank() || textRecipe.isBlank() || categoryRecipe.isBlank()) {
            Toast.makeText(activity, "Все поля должны быть заполнены", Toast.LENGTH_LONG).show()
            false
        } else true
    }

}
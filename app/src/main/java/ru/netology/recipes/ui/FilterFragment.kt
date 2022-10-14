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
import ru.netology.recipes.databinding.FragmentFilterBinding
import ru.netology.recipes.viewModel.RecipeViewModel

class FilterFragment : Fragment() {

    val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFilterBinding.inflate(inflater, container, false)

        val adapter = RecipeAdapter(viewModel)

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }

        binding.checkBoxEuropean.setText(binding.checkBoxEuropean.text.toString())
        binding.checkBoxAsian.setText(binding.checkBoxAsian.text.toString())
        binding.checkBoxPanasian.setText(binding.checkBoxPanasian.text.toString())
        binding.checkBoxEastern.setText(binding.checkBoxEastern.text.toString())
        binding.checkBoxAmerican.setText(binding.checkBoxAmerican.text.toString())
        binding.checkBoxRussian.setText(binding.checkBoxRussian.text.toString())
        binding.checkBoxMediterranean.setText(binding.checkBoxMediterranean.text.toString())

        binding.buttonApply.setOnClickListener {
            onButtonApply(binding)

        }
        return binding.root
    }

    fun onButtonApply(binding: FragmentFilterBinding) {

        var checkedCount = 7
        val categoryList = arrayListOf<String>()
        viewModel.filteredList.clear()

        if (binding.checkBoxEuropean.isChecked) {
            categoryList.add(binding.checkBoxEuropean.text.toString())
            //viewModel.showEuropean(binding.checkBoxEuropean.text.toString())
        } else --checkedCount
        //viewModel.toggleCheckEuropean = true

        if (binding.checkBoxAsian.isChecked) {
            categoryList.add(binding.checkBoxAsian.text.toString())
            //viewModel.showAsian(binding.checkBoxAsian.text.toString())
        } else --checkedCount
        //viewModel.toggleCheckAsian = true

        if (binding.checkBoxPanasian.isChecked) {
            categoryList.add(binding.checkBoxPanasian.text.toString())
            //viewModel.showPanasian(binding.checkBoxPanasian.text.toString())
        } else --checkedCount
        //viewModel.toggleCheckPanasian = true

        if (binding.checkBoxEastern.isChecked) {
            categoryList.add(binding.checkBoxEastern.text.toString())
           // viewModel.showEastern(binding.checkBoxEastern.text.toString())
        } else --checkedCount
        //viewModel.toggleCheckEastern = true

        if (binding.checkBoxAmerican.isChecked) {
            categoryList.add(binding.checkBoxAmerican.text.toString())
          //  viewModel.showAmerican(binding.checkBoxAmerican.text.toString())
        } else --checkedCount
        //viewModel.toggleCheckAmerican = true

        if (binding.checkBoxRussian.isChecked) {
            categoryList.add(binding.checkBoxRussian.text.toString())
        //    viewModel.showRussian(binding.checkBoxRussian.text.toString())
        } else --checkedCount
        //viewModel.toggleCheckRussian = true

        if (binding.checkBoxMediterranean.isChecked) {
            categoryList.add(binding.checkBoxMediterranean.text.toString())
        //    viewModel.showMediterranean(binding.checkBoxMediterranean.text.toString())
        } else --checkedCount
        //viewModel.toggleCheckMediterranean = true

        if (checkedCount == 0) {
            //viewModel.clearFilter()
            //viewModel.filterIsActive = false
            Toast.makeText(activity, "Ничего не выбрано", Toast.LENGTH_LONG)
                .show()
            findNavController().navigateUp()
        } else {
            viewModel.filteredList = categoryList
            findNavController().navigate(
                R.id.action_filterFragment_to_filterListFragment
            )

        }
    }


}










package ru.netology.recipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.recipes.adapter.RecipeAdapter
import ru.netology.recipes.databinding.FragmentFeedBinding
import ru.netology.recipes.databinding.FragmentFilterBinding
import ru.netology.recipes.viewModel.RecipeViewModel

class FilterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFilterBinding.inflate(inflater, container, false)

        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapter = RecipeAdapter(viewModel)

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }



        if (!viewModel.toggleCheckEuropean)
            binding.checkBoxEuropean.isChecked = false
        if (!viewModel.toggleCheckAsian)
            binding.checkBoxAsian.isChecked = false
        if (!viewModel.toggleCheckPanasian)
            binding.checkBoxPanasian.isChecked = false
        if (!viewModel.toggleCheckEastern)
            binding.checkBoxEastern.isChecked = false
        if (!viewModel.toggleCheckAmerican)
            binding.checkBoxAmerican.isChecked = false
        if (!viewModel.toggleCheckRussian)
            binding.checkBoxRussian.isChecked = false
        if (!viewModel.toggleCheckMediterranean)
            binding.checkBoxMediterranean.isChecked = false

        fun onApplyButtonClicked(binding: FragmentFilterBinding) {
            var checkedCount = 0

            if (!binding.checkBoxEuropean.isChecked) {
                checkedCount++
                viewModel.showEuropean("Европейская кухня")
            } else viewModel.toggleCheckEuropean = true
            if (!binding.checkBoxAsian.isChecked) {
                checkedCount++
                viewModel.showAsian("Азиатская кухня")
            } else viewModel.toggleCheckAsian = true
            if (!binding.checkBoxPanasian.isChecked) {
                checkedCount++
                viewModel.showPanasian("Паназиатская кухня")
            } else viewModel.toggleCheckPanasian = true
            if (!binding.checkBoxEastern.isChecked) {
                checkedCount++
                viewModel.showEastern("Восточная кухня")
            } else viewModel.toggleCheckEastern = true
            if (!binding.checkBoxAmerican.isChecked) {
                checkedCount++
                viewModel.showAmerican("Американская кухня")
            } else viewModel.toggleCheckAmerican = true
            if (!binding.checkBoxRussian.isChecked) {
                checkedCount++
                viewModel.showRussian("Русская кухня")
            } else viewModel.toggleCheckRussian = true
            if (!binding.checkBoxMediterranean.isChecked) {
                checkedCount++
                viewModel.showMediterranean("Средиземноморская кухня")
            } else viewModel.toggleCheckMediterranean = true

            if (checkedCount == 0) {
                viewModel.clearFilter()
                viewModel.filterIsActive = false
                findNavController().navigateUp()
            } else findNavController().navigateUp()
        }

        binding.buttonApply.setOnClickListener {
            onApplyButtonClicked(binding)
        }

        return binding.root
    }


}






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
import ru.netology.recipes.databinding.FragmentFeedBinding
import ru.netology.recipes.databinding.FragmentFilterBinding
import ru.netology.recipes.ui.UpdateFragment.Companion.idArgs
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


        if (viewModel.toggleCheckEuropean)
            binding.checkBoxEuropean.isChecked = false
        if (viewModel.toggleCheckAsian)
            binding.checkBoxAsian.isChecked = false
        if (viewModel.toggleCheckPanasian)
            binding.checkBoxPanasian.isChecked = false
        if (viewModel.toggleCheckEastern)
            binding.checkBoxEastern.isChecked = false
        if (viewModel.toggleCheckAmerican)
            binding.checkBoxAmerican.isChecked = false
        if (viewModel.toggleCheckRussian)
            binding.checkBoxRussian.isChecked = false
        if (viewModel.toggleCheckMediterranean)
            binding.checkBoxMediterranean.isChecked = false


        binding.buttonApply.setOnClickListener {
            var checkedCount = 0

            viewModel.filteredList.value?.clear()

            if (!binding.checkBoxEuropean.isChecked) {
                checkedCount++
                viewModel.showEuropean(binding.checkBoxEuropean.text.toString())
            } else viewModel.toggleCheckEuropean = true
            if (!binding.checkBoxAsian.isChecked) {
                checkedCount++
                viewModel.showAsian(binding.checkBoxAsian.text.toString())
            } else viewModel.toggleCheckAsian = true
            if (!binding.checkBoxPanasian.isChecked) {
                checkedCount++
                viewModel.showPanasian(binding.checkBoxPanasian.text.toString())
            } else viewModel.toggleCheckPanasian = true
            if (!binding.checkBoxEastern.isChecked) {
                checkedCount++
                viewModel.showEastern(binding.checkBoxEastern.text.toString())
            } else viewModel.toggleCheckEastern = true
            if (!binding.checkBoxAmerican.isChecked) {
                checkedCount++
                viewModel.showAmerican(binding.checkBoxAmerican.text.toString())
            } else viewModel.toggleCheckAmerican = true
            if (!binding.checkBoxRussian.isChecked) {
                checkedCount++
                viewModel.showRussian(binding.checkBoxRussian.text.toString())
            } else viewModel.toggleCheckRussian = true
            if (!binding.checkBoxMediterranean.isChecked) {
                checkedCount++
                viewModel.showMediterranean(binding.checkBoxMediterranean.text.toString())
            } else viewModel.toggleCheckMediterranean = true

            if (checkedCount == 0) {
                viewModel.clearFilter()
                viewModel.filterIsActive = false
                Toast.makeText(activity, "Ничего не выбрано", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigateUp()
            } else {
                viewModel.toFilterListFragment.observe(viewLifecycleOwner) {
                    findNavController().navigate(
                        R.id.action_filterFragment_to_filterListFragment)
                }
            }
        }

        return binding.root
    }
}









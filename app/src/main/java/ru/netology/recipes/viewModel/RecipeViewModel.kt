package ru.netology.recipes.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.util.SingleLiveEvent
import ru.netology.recipes.adapter.RecipeInteractionListener
import ru.netology.recipes.db.AppDb
import ru.netology.recipes.dto.Recipe
import ru.netology.recipes.repository.RecipeRepository
import ru.netology.recipes.repository.RecipeRepositoryImpl

class RecipeViewModel(application: Application) : AndroidViewModel(application),
    RecipeInteractionListener {

    private val repository: RecipeRepository =
        RecipeRepositoryImpl(dao = AppDb.getInstance(context = application).recipeDao)

    val data by repository::data
    var filterIsActive = false
    val toFavoriteFragment = SingleLiveEvent<String>()
    val toUpdateFragment = SingleLiveEvent<String?>()
    val toSingleFragment = SingleLiveEvent<Long>()
    val toFilterFragment = SingleLiveEvent<Unit>()
    val toFilterListFragment = SingleLiveEvent<Unit>()
    val updateRecipe = MutableLiveData<Recipe>(null)
    private val currentRecipe = MutableLiveData<Recipe?>(null)
    var filteredList = mutableListOf<String>()


    fun clearFilter() {
        repository.getData()
    }

    override fun updateContent(
        id: Long,
        title: String,
        authorName: String,
        categoryRecipe: String,
        textRecipe: String
    ) {
        val recipe = Recipe(
            id = id,
            title = title,
            authorName = authorName,
            categoryRecipe = categoryRecipe,
            textRecipe = textRecipe
        )
        repository.save(recipe)
        //currentRecipe.value = null

    }

    override fun onRemoveClicked(recipe: Recipe) {
        repository.delete(recipe)
    }

    override fun onEditClicked(recipe: Recipe) {
        updateRecipe.value = recipe
        toUpdateFragment.call()
    }

    override fun onFavouriteClicked(recipeId: Long) {
        repository.favourite(recipeId)
    }


    override fun onSearchClicked(text: String) {
        repository.searchText(text)
    }

 //   override fun onCreateClicked() {
 //       toCreateFragment.call()
 //   }


    override fun onSaveButtonClicked(
        title: String,
        authorName: String,
        categoryRecipe: String,
        textRecipe: String
    ) { //сохранение отредактированного или нового рецепта
        if (title.isBlank()
            && authorName.isBlank()
            && categoryRecipe.isBlank()
            && textRecipe.isBlank()
        ) return

        val recipe = currentRecipe.value?.copy(
            title = title,
            authorName = authorName,
            categoryRecipe = categoryRecipe,
            textRecipe = textRecipe
        ) ?: Recipe(
            id = RecipeRepository.NEW_ID,
            title = title,
            authorName = authorName,
            categoryRecipe = categoryRecipe,
            textRecipe = textRecipe
        )
        repository.save(recipe)
        currentRecipe.value =
            null // сброс содержимого сохраненного рецепта в строке, где мы его печатали
    }

    override fun onSingleRecipeClicked(recipe: Recipe) {
        toSingleFragment.value = recipe.id

    }

//    var toggleCheckEuropean = true
//    var toggleCheckAsian = true
//    var toggleCheckPanasian = true
//    var toggleCheckEastern = true
//    var toggleCheckAmerican = true
//    var toggleCheckRussian = true
//    var toggleCheckMediterranean = true
//
//    fun showEuropean(categoryRecipe: String) {
//        repository.showEuropean(categoryRecipe)
//        filterIsActive = true
//        toggleCheckEuropean = false
//    }
//
//    fun showAsian(categoryRecipe: String) {
//        repository.showAsian(categoryRecipe)
//        filterIsActive = true
//        toggleCheckAsian = false
//    }
//
//    fun showPanasian(categoryRecipe: String) {
//        repository.showPanasian(categoryRecipe)
//        filterIsActive = true
//        toggleCheckPanasian = false
//    }
//
//    fun showEastern(categoryRecipe: String) {
//        repository.showEastern(categoryRecipe)
//        filterIsActive = true
//        toggleCheckEastern = false
//    }
//
//    fun showAmerican(categoryRecipe: String) {
//        repository.showAmerican(categoryRecipe)
//        filterIsActive = true
//        toggleCheckAmerican = false
//    }
//
//    fun showRussian(categoryRecipe: String) {
//        repository.showRussian(categoryRecipe)
//        filterIsActive = true
//        toggleCheckRussian = false
//    }
//
//    fun showMediterranean(categoryRecipe: String) {
//        repository.showMediterranean(categoryRecipe)
//        filterIsActive = true
//        toggleCheckMediterranean = false
//    }




}
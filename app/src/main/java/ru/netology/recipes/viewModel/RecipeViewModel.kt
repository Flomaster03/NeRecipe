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
    val toFavoriteFragment = SingleLiveEvent<String>()
    val toUpdateFragment = SingleLiveEvent<String?>()
    val toSingleFragment = SingleLiveEvent<Long>()
    val toFilterFragment = SingleLiveEvent<Unit>()
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
}
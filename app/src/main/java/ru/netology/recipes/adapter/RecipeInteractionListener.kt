package ru.netology.recipes.adapter

import ru.netology.recipes.dto.Recipe

interface RecipeInteractionListener {

    fun onRemoveClicked(recipe: Recipe)
    fun onEditClicked(recipe: Recipe)
    fun onFavouriteClicked(recipeId: Long)
    fun onSearchClicked(text: String)
    fun onCreateClicked()
    fun updateContent(id: Long, title: String, authorName: String, categoryRecipe: String, textRecipe: String)
    fun onSaveButtonClicked( title: String, authorName: String, categoryRecipe: String, textRecipe: String)
    fun onSingleRecipeClicked(recipe: Recipe)

}
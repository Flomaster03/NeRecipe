package ru.netology.recipes.repository

import androidx.lifecycle.LiveData
import ru.netology.recipes.dto.Recipe

interface RecipeRepository {

    val data: LiveData<List<Recipe>>

    fun save(recipe: Recipe)
    fun update(recipe: Recipe)
    fun delete(recipe: Recipe)
    fun favourite(long: Long)
    fun searchText(Text: String)
    fun getData()

    companion object{
        const val NEW_ID = 0L
    }
}
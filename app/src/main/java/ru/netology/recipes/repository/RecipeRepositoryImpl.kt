package ru.netology.recipes.repository

import androidx.lifecycle.map
import ru.netology.recipes.db.RecipeDao
import ru.netology.recipes.db.toEntity
import ru.netology.recipes.db.toModel
import ru.netology.recipes.dto.Recipe

class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {

    override var data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun getData() {
        data = dao.getAll().map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepository.NEW_ID) dao.save(recipe = recipe.toEntity())
        else dao.updateContentById(
            recipe.id, recipe.title, recipe.authorName,
            recipe.categoryRecipe, recipe.textRecipe
        )
    }

    override fun update(recipe: Recipe) {
        save(recipe)
    }

    override fun delete(recipe: Recipe) {
        dao.removeById(recipe.id)
    }

    override fun favourite(recipeId: Long) {
        dao.favById(recipeId)
    }

   override fun searchText(Text: String) {
       data = dao.searchByText(Text).map { entities ->
           entities.map { it.toModel() }
       }
   }
}
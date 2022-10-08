package ru.netology.recipes.dto

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: Long,
    val title: String,
    val authorName: String,
    val categoryRecipe: String,
    val textRecipe: String,
    val isFavourite: Boolean = false
)
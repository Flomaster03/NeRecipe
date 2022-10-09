package ru.netology.recipes.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
class RecipeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "authorName")
    val authorName: String,
    @ColumnInfo(name = "categoryRecipe")
    val categoryRecipe: String,
    @ColumnInfo(name = "textRecipe")
    val textRecipe: String,
    @ColumnInfo(name = "isFavorite")
    val isFavourite: Boolean,
)
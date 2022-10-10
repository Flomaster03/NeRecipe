package ru.netology.recipes.dto


import kotlinx.serialization.Serializable

@Serializable

enum class Category {
    European,
    Asian,
    PanAsian,
    Eastern,
    American,
    Russian,
    Mediterranean
}

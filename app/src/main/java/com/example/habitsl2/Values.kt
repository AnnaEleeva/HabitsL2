package com.example.habitsl2

const val CREATE_CODE = 228
const val EDIT_CODE = 322

val priorities = listOf(
    "Низкий",
    "Средний",
    "Высокий",
    "Очень высокий"
)

val prioritiesMap = mapOf(
    Priority.Low to priorities[0],
    Priority.Medium to priorities[1],
    Priority.High to priorities[2],
    Priority.SuperHigh to priorities[3]
)

val typesMap = mapOf(
    HabitType.Useful to "Полезная",
    HabitType.Destructive to "Деструктиваная",
    HabitType.Neutral to "Нейтральная",
    HabitType.Bounding to "Ограничивающая"

)
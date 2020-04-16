package com.example.habitsl2

import java.io.Serializable

enum class HabitType {
    Useful,
    Destructive,
    Neutral,
    Bounding

}

enum class Priority {
    Low,
    Medium,
    High,
    SuperHigh
}

class Habit(
    var name: String,
    var description: String,
    var priority: Priority,
    var type: HabitType,
    var repeats: Int,
    var period: Int
): Serializable
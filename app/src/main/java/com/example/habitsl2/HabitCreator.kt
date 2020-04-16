package com.example.habitsl2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_habit_creator.*

class HabitCreator : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var usefulRB: RadioButton
    private lateinit var destructiveRB: RadioButton
    private lateinit var neutralRB: RadioButton
    private lateinit var boundingRB: RadioButton
    private lateinit var repeatsEditText: EditText
    private lateinit var periodEditText: EditText
    private lateinit var createButton: Button

    private var priorityText: String = ""
    private lateinit var adapter: ArrayAdapter<String>
    private var mode = "Create"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_creator)

        init()

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            priorities
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            prioritySpinner.adapter = adapter
        }

        mode = intent.extras?.getString("Mode")!!
        if (mode == "Edit") {
            title = "Изменить привычку"
            fillEditingObject()
        } else if (mode == "Create") {
            title = "Создать новую привычку"
        }

        prioritySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                priorityText = ""
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                priorityText = parent?.getItemAtPosition(position).toString()
            }

        }

        createButton.setOnClickListener {
            val habit = getNewHabit()
            if (habit != null) {
                val intent = Intent().apply {
                    putExtra("NEW_HABIT", habit)
                }
                val code = when (mode) {
                    "Create" -> CREATE_CODE
                    "Edit" -> EDIT_CODE
                    else -> 0
                }
                setResult(code, intent)
                finish()
            }
        }
    }

    private fun init() {
        nameEditText = findViewById(R.id.nameEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        prioritySpinner = findViewById(R.id.prioritySpinner)
        usefulRB = findViewById(R.id.usefulRB)
        destructiveRB = findViewById(R.id.destructiveRB)
        neutralRB = findViewById(R.id.neutralRB)
        boundingRB = findViewById(R.id.boundingRB)
        repeatsEditText = findViewById(R.id.repeatsEditText)
        periodEditText = findViewById(R.id.periodEditText)
        createButton = findViewById(R.id.createButton)
    }

    private fun fillEditingObject() {
        val habit: Habit = intent.extras?.getSerializable("Editable") as Habit
        nameEditText.setText(habit.name)
        descriptionEditText.setText(habit.description)
        prioritySpinner.setSelection(adapter.getPosition(prioritiesMap[habit.priority]))
        when (habit.type) {
            HabitType.Useful -> usefulRB.isChecked = true
            HabitType.Destructive -> destructiveRB.isChecked = true
            HabitType.Neutral -> neutralRB.isChecked = true
            HabitType.Bounding ->boundingRB.isChecked =true
        }
        repeatsEditText.setText(habit.repeats.toString())
        periodEditText.setText(habit.period.toString())
    }

    private fun getNewHabit(): Habit? {
        val name = nameEditText.text.toString()
        if (name.isEmpty())
            return null
        val description = descriptionEditText.text.toString()
        if (priorityText.isEmpty())
            return null
        lateinit var priority: Priority
        when (priorityText) {
            priorities[0] -> {
                priority = Priority.Low
            }
            priorities[1] -> {
                priority = Priority.Medium
            }
            priorities[2] -> {
                priority = Priority.High
            }
            priorities[3] -> {
                priority = Priority.SuperHigh
            }
        }
        lateinit var type: HabitType
        when {
            usefulRB.isChecked -> {
                type = HabitType.Useful
            }
            destructiveRB.isChecked -> {
                type = HabitType.Destructive
            }

            neutralRB.isChecked -> {
                type = HabitType.Neutral
            }
            boundingRB.isChecked -> {
                type = HabitType.Bounding
            }
        }
        val repeatsString = repeatsEditText.text.toString()
        if (repeatsString.isEmpty())
            return null
        val repeats = repeatsString.toInt()
        val periodString = periodEditText.text.toString()
        if (periodString.isEmpty())
            return null
        val period = periodString.toInt()
        return Habit(
            name, description, priority, type, repeats, period
        )
    }
}
package com.example.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_editor.*

class Editor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        val position = intent.getIntExtra("position", 0)
        editedFirstName.setText(intent.getStringExtra("firstName"))
        editedLastName.setText(intent.getStringExtra("lastName"))

        saveButton.setOnClickListener { saveChanges(position) }
    }

    private fun saveChanges(position: Int) {
        val intent = Intent()
        intent.putExtra("position", position)
        intent.putExtra("editedFirstName", editedFirstName.text.toString())
        intent.putExtra("editedLastName", editedLastName.text.toString())
        setResult(RESULT_OK, intent)
        finish()
    }

}
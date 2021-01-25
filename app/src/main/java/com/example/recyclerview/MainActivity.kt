package com.example.recyclerview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var data1: ArrayList<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = Adapter(this, data1!!)

        setInsertButton()

        saveButton.setOnClickListener { saveData() }

        clearDataButton.setOnClickListener { clearData() }
    }

    private fun clearData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("data_list", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun saveData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("data_list", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        editor.putString("list", gson.toJson(data1))
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("data_list", Context.MODE_PRIVATE)
        val list = sharedPreferences.getString("list", null)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<User>>(){}.type
        data1 = gson.fromJson(list, type)

        if (data1 == null) {
            data1 = arrayListOf()
        }
    }

    private fun setInsertButton() {
        insertButton.setOnClickListener {
            data1!!.add(User(editFirstName.text.toString(), editLastName.text.toString()))

            editFirstName.text.clear()
            editLastName.text.clear()

            recyclerview.adapter?.notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                val firstName = data?.getStringExtra("editedFirstName")
                val lastName = data?.getStringExtra("editedLastName")
                val position = data?.getIntExtra("position", 0)

                if (firstName != null) {
                    data1!![position!!].firstName = firstName
                }
                if (lastName != null) {
                    data1!![position!!].lastName = lastName
                }

                recyclerview.adapter?.notifyDataSetChanged()
            }
        }
    }
}
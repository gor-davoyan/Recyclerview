package com.example.recyclerview

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val activity: Activity, private val data: ArrayList<User>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.firstName.text = data[position].firstName
        holder.lastName.text = data[position].lastName

        holder.clear.setOnClickListener { remove(position) }

        holder.item.setOnClickListener { edit(position) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun edit(position: Int) {
        val intent = Intent(activity, Editor::class.java)
        intent.putExtra("firstName", data[position].firstName)
        intent.putExtra("lastName", data[position].lastName)
        intent.putExtra("position", position)
        activity.startActivityForResult(intent, 1)
    }

    private fun remove(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val firstName: TextView = view.findViewById(R.id.editedFirstName)
        val lastName: TextView = view.findViewById(R.id.editedLastName)
        val clear: ImageView = view.findViewById(R.id.clearButton)
        val item: View = view.findViewById(R.id.item)
    }
}
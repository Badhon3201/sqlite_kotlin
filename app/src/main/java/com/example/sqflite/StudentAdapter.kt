package com.example.sqflite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var stdList: ArrayList<StudentModel> = ArrayList()

    private var onClickItem: ((StudentModel) -> Unit)? = null

    private var onClickItemDelete: ((StudentModel) -> Unit)? = null

    fun setOnClickItemDelete(callback: (StudentModel) -> Unit) {
        this.onClickItemDelete = callback
    }

    fun setOnClickItem(callback: (StudentModel) -> Unit) {
        this.onClickItem = callback
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): StudentAdapter.StudentViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item_std, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentAdapter.StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.btnView(std)
        holder.itemView.setOnClickListener { onClickItem?.invoke(std) }
        holder.btnDelete.setOnClickListener {
            onClickItemDelete?.invoke(std)
        }
    }

    override fun getItemCount(): Int {
        return stdList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(items: ArrayList<StudentModel>) {
        this.stdList = items

        notifyDataSetChanged()
    }


    class StudentViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun btnView(std: StudentModel) {
            id.text = std.id.toString()
            name.text = std.name
            email.text = std.email
        }
    }
}












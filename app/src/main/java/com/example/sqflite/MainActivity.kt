package com.example.sqflite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        initRecycleView()

        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addStudent() }

        btnView.setOnClickListener {
            getStudent()
        }

        btnUpdate.setOnClickListener {
            updateStudent()
        }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_LONG).show()
            edEmail.setText(it.email)
            edName.setText(it.name)
            std = it
        }

        adapter?.setOnClickItemDelete {
            deleteStudent(id = it.id)
        }

    }

    private fun getStudent() {
        val stdList = sqLiteHelper.getAllStudent()
        Log.d("PPPP", "${stdList[0].email}}")

        adapter?.addItem(stdList)
    }

    private fun addStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please enter required field", Toast.LENGTH_LONG).show()
        } else {
            val std = StudentModel(name = name, email = email)
            val status = sqLiteHelper.insertStudent(std)

            if (status > -1) {
                Toast.makeText(this, "Student Added...", Toast.LENGTH_LONG).show()
                clearEditText()
                getStudent()
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name == std?.name && email == std?.email) {
            Toast.makeText(this, "Record not changed...", Toast.LENGTH_LONG).show()
            return
        }
        if (std == null) return

        var std = StudentModel(id = std!!.id, name = name, email = email)

        Log.d("ChangeValue", "${name + email}")

        val status = sqLiteHelper.updateStudent(std)

        if (status > -1) {
            clearEditText()
            getStudent()
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteStudent(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you want to delete this item")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqLiteHelper.deleteStudent(id)
            getStudent()
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()

    }

    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
        edEmail.requestFocus()
    }

    private fun initRecycleView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recycleView)
    }
}
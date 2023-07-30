package com.example.sqflite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        sqLiteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addStudent() }

        btnView.setOnClickListener {
            getStudent()
        }
    }

    private fun getStudent() {
        val stdList = sqLiteHelper.getAllStudent()
        Log.d("PPPP", "${stdList[0].email}}")
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
            } else {
                Toast.makeText(this, "Record not saved", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
        edEmail.requestFocus()
    }

    private fun initView() {
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
    }
}
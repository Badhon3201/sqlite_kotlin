package com.example.sqflite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "student.db"
        private const val TBL_STUDENT = "tbl_student"
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblStudent = ("CREATE TABLE " + TBL_STUDENT + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT," + EMAIL + " TEXT" + ")")

        db?.execSQL(createTblStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")

        onCreate(db)
    }


    fun insertStudent(std: StudentModel): Long {
        val db = this.writableDatabase

        val contantValue = ContentValues()

        contantValue.put(ID, std.id)
        contantValue.put(NAME, std.name)
        contantValue.put(EMAIL, std.email)

        val success = db.insert(TBL_STUDENT, null, contantValue)

        db.close()

        return success
    }

    @SuppressLint("Range")
    fun getAllStudent(): ArrayList<StudentModel> {
        val stdList: ArrayList<StudentModel> = ArrayList()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TBL_STUDENT"

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }


        var id: Int
        var name: String
        var email: String


        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                email = cursor.getString(cursor.getColumnIndex("email"))

                val std = StudentModel(id = id, name = name, email = email)
                stdList.add(std)

            } while (cursor.moveToNext())
        }
        return stdList
    }


    fun updateStudent(std: StudentModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id.toString())
        contentValues.put(NAME, std.name)
        contentValues.put(EMAIL, std.email)

        Log.d("ID","${std.id}")
        Log.d("Name", std.name)
        Log.d("Email", std.email)

//        val success = db.update(TBL_STUDENT, contentValues,"id " + std.id,null)
        val success = db.update(TBL_STUDENT, contentValues, "id=" + std.id, null)
        db.close()
        return success
    }

    fun deleteStudent(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_STUDENT, "id=" + id, null)
        db.close()
        return success
    }

}












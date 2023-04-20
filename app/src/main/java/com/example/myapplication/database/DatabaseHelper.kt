package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_NAME (ID INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT, NAME TEXT,BRAND TEXT,TYPE_BODY TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun createData(name: String, brand: String, typeBody: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        with(contentValues) {
            put(COLUMN2, name)
            put(COLUMN3, brand)
            put(COLUMN4, typeBody)
        }
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun updateData(id: String, name: String, brand: String, typeBody: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        with(contentValues) {
            put(COLUMN1, id)
            put(COLUMN2, name)
            put(COLUMN3, brand)
            put(COLUMN4, typeBody)
        }
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    fun deleteData(id: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "ID = ?", arrayOf(id))
    }

    fun readData(): Cursor {
        val db = this.writableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    companion object {
        const val DATABASE_NAME = "cars.db"
        const val TABLE_NAME = "cars_table"
        const val DATABASE_VERSION = 1
        const val COLUMN1 = "ID"
        const val COLUMN2 = "Name"
        const val COLUMN3 = "Brand"
        const val COLUMN4 = "TypeBody"
    }
}
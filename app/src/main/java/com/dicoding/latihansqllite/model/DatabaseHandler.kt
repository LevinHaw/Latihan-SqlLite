package com.dicoding.latihansqllite.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.hardware.biometrics.BiometricManager.Strings

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

        companion object {
            private const val DATABASE_VERSION = 1

            // Database name
            private const val DATABASE_NAME = "DB_APP"

            // Table name
            private const val TABLE_USER = "USER"

            // Column Table
            private const val KEY_ID = "id"
            private const val KEY_NAME = "name"
            private const val KEY_PHONENUM = "phone_num"
            private const val KEY_EMAIL = "email"


        }

    override fun onCreate(db: SQLiteDatabase?) {
        val queryCreateTable = ("CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PHONENUM + " TEXT," + KEY_EMAIL + " TEXT)")

        db?.execSQL(queryCreateTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    fun insert(userModels: User) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, userModels.name)
        values.put(KEY_PHONENUM, userModels.phoneNumber)
        values.put(KEY_EMAIL, userModels.email)
        db.insert(TABLE_USER, null, values)
        db.close()

    }

    fun getAllUser(): List<User>? {
        val users : MutableList<User> = ArrayList<User>()

        // Select all query
        val selectQuery = "SELECT * FROM $TABLE_USER"
        val db = this.writableDatabase
        val cursor : Cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val userModels = User(
                    id = cursor.getString(0).toInt(),
                    name = cursor.getString(1),
                    phoneNumber = cursor.getString(2),
                    email = cursor.getString(3)
                )
                users.add(userModels)
            } while (cursor.moveToNext())
        }
        return users
    }

    fun delete(user : User) {
        val db = this.writableDatabase
        db.delete(TABLE_USER, "$KEY_ID = ?", arrayOf(String.format(user.id.toString())))
        db.close()
    }

    fun updateUser(user : User) : Int{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, user.name)
        values.put(KEY_PHONENUM, user.phoneNumber)
        values.put(KEY_EMAIL, user.email)

        // Updating data
        return db.update(
            TABLE_USER,
            values,
            "$KEY_ID = ?",
            arrayOf(String.format(user.id.toString()))
        )
    }
}
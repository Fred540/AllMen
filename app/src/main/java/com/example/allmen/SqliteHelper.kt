package com.example.allmen

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class SqliteHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS)
    }

    //using this method we can add users to user table
    fun addUser(user: User) {

        //get writable database
        val db = this.writableDatabase

        //create content values to insert
        val values = ContentValues()

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName)

        //Put email in  @values
        values.put(KEY_EMAIL, user.email)

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password)

        // insert row
        val todo_id = db.insert(TABLE_USERS, null, values)
    }

    fun Authenticate(user: User): User? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(
                KEY_ID,
                KEY_USER_NAME,
                KEY_EMAIL,
                KEY_PASSWORD
            ),  //Selecting columns want to query
            KEY_EMAIL + "=?",
            arrayOf(user.email),  //Where clause
            null,
            null,
            null
        )
        if (cursor != null && cursor.moveToFirst() && cursor.count > 0) {
            //if cursor has value then in user database there is user associated with this given email
            val user1 = User(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)
            )

            //Match both passwords check they are same or not
            if (user.password.equals(user1.password, ignoreCase = true)) {
                return user1
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null
    }

    fun isEmailExists(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            arrayOf(
                KEY_ID,
                KEY_USER_NAME,
                KEY_EMAIL,
                KEY_PASSWORD
            ),  //Selecting columns want to query
            KEY_EMAIL + "=?",
            arrayOf(email),  //Where clause
            null,
            null,
            null
        )
        return if (cursor != null && cursor.moveToFirst() && cursor.count > 0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            true
        } else false

        //if email does not exist return false
    }

    companion object {
        //DATABASE NAME
        const val DATABASE_NAME = "loopwiki.com"

        //DATABASE VERSION
        const val DATABASE_VERSION = 1

        //TABLE NAME
        const val TABLE_USERS = "users"

        //TABLE USERS COLUMNS
        //ID COLUMN @primaryKey
        const val KEY_ID = "id"

        //COLUMN user name
        const val KEY_USER_NAME = "username"

        //COLUMN email
        const val KEY_EMAIL = "email"

        //COLUMN password
        const val KEY_PASSWORD = "password"

        //SQL for creating users table
        const val SQL_TABLE_USERS = (" CREATE TABLE " + TABLE_USERS
                + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_USER_NAME + " TEXT, "
                + KEY_EMAIL + " TEXT, "
                + KEY_PASSWORD + " TEXT"
                + " ) ")
    }
}
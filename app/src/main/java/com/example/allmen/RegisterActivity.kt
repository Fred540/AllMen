package com.example.allmen

import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class RegisterActivity : AppCompatActivity() {
    //Declaration EditTexts
    var editTextUserName: EditText? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null

    //Declaration TextInputLayout
    var textInputLayoutUserName: TextInputLayout? = null
    var textInputLayoutEmail: TextInputLayout? = null
    var textInputLayoutPassword: TextInputLayout? = null

    //Declaration Button
    var buttonRegister: Button? = null

    //Declaration SqliteHelper
    var sqliteHelper: SqliteHelper? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        sqliteHelper = SqliteHelper(this)
        initTextViewLogin()
        initViews()
        buttonRegister!!.setOnClickListener {
            if (validate()) {
                val UserName = editTextUserName!!.text.toString()
                val Email = editTextEmail!!.text.toString()
                val Password = editTextPassword!!.text.toString()

                //Check in the database is there any user associated with  this email
                if (!sqliteHelper!!.isEmailExists(Email)) {

                    //Email does not exist now add new user to database
                    sqliteHelper!!.addUser(User(null, UserName, Email, Password))
                    Snackbar.make(
                        buttonRegister!!,
                        "User created successfully! Please Login ",
                        Snackbar.LENGTH_LONG
                    ).show()
                    Handler().postDelayed({ finish() }, Snackbar.LENGTH_LONG.toLong())
                } else {

                    //Email exists with email input provided so show error user already exist
                    Snackbar.make(
                        buttonRegister!!,
                        "User already exists with same email ",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    //this method used to set Login TextView click event
    private fun initTextViewLogin() {
        val textViewLogin = findViewById<View>(R.id.textViewLogin) as TextView
        textViewLogin.setOnClickListener { finish() }
    }

    //this method is used to connect XML views to its Objects
    private fun initViews() {
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        editTextUserName = findViewById<View>(R.id.editTextUserName) as EditText
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword =
            findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        textInputLayoutUserName =
            findViewById<View>(R.id.textInputLayoutUserName) as TextInputLayout
        buttonRegister = findViewById<View>(R.id.buttonRegister) as Button
    }

    //This method is used to validate input given by user
    fun validate(): Boolean {
        var valid = false

        //Get values from EditText fields
        val UserName = editTextUserName!!.text.toString()
        val Email = editTextEmail!!.text.toString()
        val Password = editTextPassword!!.text.toString()

        //Handling validation for UserName field
        if (UserName.isEmpty()) {
            valid = false
            textInputLayoutUserName!!.error = "Please enter valid username!"
        } else {
            if (UserName.length > 5) {
                valid = true
                textInputLayoutUserName!!.error = null
            } else {
                valid = false
                textInputLayoutUserName!!.error = "Username is to short!"
            }
        }

        //Handling validation for Email field
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            valid = false
            textInputLayoutEmail!!.error = "Please enter valid email!"
        } else {
            valid = true
            textInputLayoutEmail!!.error = null
        }

        //Handling validation for Password field
        if (Password.isEmpty()) {
            valid = false
            textInputLayoutPassword!!.error = "Please enter valid password!"
        } else {
            if (Password.length > 5) {
                valid = true
                textInputLayoutPassword!!.error = null
            } else {
                valid = false
                textInputLayoutPassword!!.error = "Password is to short!"
            }
        }
        return valid
    }
}
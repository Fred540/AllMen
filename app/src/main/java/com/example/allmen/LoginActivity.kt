package com.example.allmen

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {
    //Declaration EditTexts
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null

    //Declaration TextInputLayout
    var textInputLayoutEmail: TextInputLayout? = null
    var textInputLayoutPassword: TextInputLayout? = null

    //Declaration Button
    var buttonLogin: Button? = null

    //Declaration SqliteHelper
    var sqliteHelper: SqliteHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sqliteHelper = SqliteHelper(this)
        initCreateAccountTextView()
        initViews()

        //set click event of login button
        buttonLogin!!.setOnClickListener {
            //Check user input is correct or not
            if (validate()) {

                //Get values from EditText fields
                val Email = editTextEmail!!.text.toString()
                val Password = editTextPassword!!.text.toString()

                //Authenticate user
                val currentUser: User? = sqliteHelper!!.Authenticate(User(null, null, Email, Password))

                //Check Authentication is successful or not
                if (currentUser != null) {
                    Snackbar.make(buttonLogin!!, "Successfully Logged in!", Snackbar.LENGTH_LONG)
                        .show()

                    //User Logged in Successfully Launch You home screen activity
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {

                    //User Logged in Failed
                    Snackbar.make(
                        buttonLogin!!,
                        "Failed to log in , please try again",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    //this method used to set Create account TextView text and click event( maltipal colors
    // for TextView yet not supported in Xml so i have done it programmatically)
    private fun initCreateAccountTextView() {
        val textViewCreateAccount = findViewById<View>(R.id.textViewCreateAccount) as TextView
        textViewCreateAccount.text =
            fromHtml("<font color='#ffffff'>I don't have account yet. </font><font color='#0c0099'>create one</font>")
        textViewCreateAccount.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    //this method is used to connect XML views to its Objects
    private fun initViews() {
        editTextEmail = findViewById<View>(R.id.editTextEmail) as EditText
        editTextPassword = findViewById<View>(R.id.editTextPassword) as EditText
        textInputLayoutEmail = findViewById<View>(R.id.textInputLayoutEmail) as TextInputLayout
        textInputLayoutPassword =
            findViewById<View>(R.id.textInputLayoutPassword) as TextInputLayout
        buttonLogin = findViewById<View>(R.id.buttonLogin) as Button
    }

    //This method is used to validate input given by user
    fun validate(): Boolean {
        var valid = false

        //Get values from EditText fields
        val Email = editTextEmail!!.text.toString()
        val Password = editTextPassword!!.text.toString()

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

    companion object {
        //This method is for handling fromHtml method deprecation
        fun fromHtml(html: String?): Spanned {
            val result: Spanned
            result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
            return result
        }
    }
}
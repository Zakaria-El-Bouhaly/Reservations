package com.example.scheduler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class Register : AppCompatActivity() {
    private lateinit var usernamefield: EditText
    private lateinit var psdfield: EditText
    private lateinit var psdconfrfield: EditText
    private lateinit var registerbtn: Button
    private lateinit var loginbtn: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernamefield = findViewById(R.id.editTextTextPersonName)
        psdfield = findViewById(R.id.pass)
        psdconfrfield = findViewById(R.id.psd2)

        registerbtn = findViewById(R.id.registerbtn)
        loginbtn = findViewById(R.id.signin)

        mAuth = FirebaseAuth.getInstance()

        var intent: Intent?

        loginbtn.setOnClickListener {
            intent = Intent(this@Register, Login::class.java)
            startActivity(intent)
            finish()
        }

        registerbtn.setOnClickListener {


            val username: String = usernamefield.text.toString()
            val pass: String = psdfield.text.toString()
            val pass_conf: String = psdconfrfield.text.toString()

            if (username != "" && pass != "" && pass_conf != "") {

                if (pass != pass_conf) {
                    val toast = Toast.makeText(this, "passwords don't match", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()

                } else {

                    // on below line we are creating a new user by passing email and password.
                    mAuth.createUserWithEmailAndPassword(username, pass).addOnCompleteListener(
                        object : OnCompleteListener<AuthResult> {
                            override fun onComplete(task: Task<AuthResult>) {
                                if (task.isSuccessful) {

                                    Toast.makeText(
                                        this@Register,
                                        "User Registered..",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val i = Intent(this@Register, Login::class.java)
                                    startActivity(i)
                                    finish()
                                } else {

                                    Toast.makeText(
                                        this@Register,
                                        "Fail to register user..",
                                        Toast.LENGTH_SHORT
                                    )
                                        .show()
                                }

                            }

                        }
                    )
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = mAuth.currentUser
        if (user != null) {
            // if the user is not null then we are
            // opening a main activity on below line.
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
            finish()
        }
    }

}

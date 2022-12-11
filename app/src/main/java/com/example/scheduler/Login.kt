package com.example.scheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class Login : AppCompatActivity() {
    private lateinit var usernamefield: EditText
    private lateinit var psdfield: EditText
    private lateinit var registerbtn: Button
    private lateinit var loginbtn: Button

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernamefield = findViewById(R.id.emailfield)
        psdfield = findViewById(R.id.pass)

        registerbtn = findViewById(R.id.registernow)
        mAuth = FirebaseAuth.getInstance()
        loginbtn = findViewById(R.id.signin)

        var intent: Intent? = null


        registerbtn.setOnClickListener {
            intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }

        loginbtn.setOnClickListener {

            val username: String = usernamefield.text.toString()
            val pass: String = psdfield.text.toString()

            mAuth?.signInWithEmailAndPassword(username, pass)?.addOnCompleteListener(
                object : OnCompleteListener<AuthResult> {
                    override fun onComplete(task: Task<AuthResult>) {
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@Login,
                                "Logged In.." + mAuth?.currentUser.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            val i = Intent(this@Login, Add_appointment::class.java)
                            startActivity(i)
                            finish()

                        } else {
                            Toast.makeText(this@Login, "Fail to login..", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                }
            )
        }
    }

    override fun onStart() {
        super.onStart()
        val user: FirebaseUser? = mAuth?.currentUser
        if (user != null) {
            // if the user is not null then we are
            // opening a main activity on below line.
            val i = Intent(this, Add_appointment::class.java)
            startActivity(i)
            finish()
        }
    }
}

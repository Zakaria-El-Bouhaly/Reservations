package com.example.scheduler

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
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


class Login : AppCompatActivity() {
    private lateinit var usernamefield: EditText
    private lateinit var psdfield: EditText
    private lateinit var registerbtn: Button
    private lateinit var loginbtn: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignIn: SignInButton
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernamefield = findViewById(R.id.emailfield)
        psdfield = findViewById(R.id.pass)

        registerbtn = findViewById(R.id.registernow)
        mAuth = FirebaseAuth.getInstance()
        loginbtn = findViewById(R.id.signin)

        googleSignIn = findViewById(R.id.google_signin)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        var intent: Intent


        registerbtn.setOnClickListener {
            intent = Intent(this, Register::class.java)
            startActivity(intent)
            finish()
        }

        loginbtn.setOnClickListener {

            val username: String = usernamefield.text.toString()
            val pass: String = psdfield.text.toString()


            if (username != "" && pass != "") {
                mAuth.signInWithEmailAndPassword(username, pass).addOnCompleteListener(
                    object : OnCompleteListener<AuthResult> {
                        override fun onComplete(task: Task<AuthResult>) {
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@Login,
                                    "Logged In.." + mAuth.currentUser.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                val i = Intent(this@Login, HomeActivity::class.java)
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

        googleSignIn.setOnClickListener {
            signInGoogle()
        }

    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    if (account != null) {
                        updateUI(account)
                    }
                } else {
                    Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }


    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val intent = Intent(this, HomeActivity::class.java)
//                intent.putExtra("email", account.email)
//                intent.putExtra("name", account.displayName)
                startActivity(intent)
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

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

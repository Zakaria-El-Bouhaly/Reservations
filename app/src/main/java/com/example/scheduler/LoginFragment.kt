package com.example.scheduler

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.scheduler.Models.LoginDto
import com.example.scheduler.Models.User
import com.example.scheduler.databinding.FrgmtLoginBinding
import com.example.scheduler.helpers.Redirect
import com.example.scheduler.viewModels.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FrgmtLoginBinding

    private lateinit var usernamefield: EditText
    private lateinit var psdfield: EditText
    private lateinit var loginbtn: Button
    private lateinit var googleSignIn: Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var userViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FrgmtLoginBinding.inflate(inflater)

        usernamefield = binding.emailfield.editText!!
        psdfield = binding.pass.editText!!

        userViewModel =
            ViewModelProvider(this, defaultViewModelProviderFactory).get(UserViewModel::class.java)

        loginbtn = binding.signin

        googleSignIn = binding.googleSignBtn

        loginbtn.setOnClickListener {
            val username: String = usernamefield.text.toString()
            val pass: String = psdfield.text.toString()

            if (username != "" && pass != "") {
                val loginCred = LoginDto(username, pass)
                var result = userViewModel.login(loginCred)
                Log.i("TRUU2", result.toString())
                if (result) {
                    Toast.makeText(
                        activity,
                        "Logged In",
                        Toast.LENGTH_SHORT
                    ).show()
                    redirectHome(User("", "", ""))
                } else {
                    Toast.makeText(activity, "Fail to login..", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(activity as Activity, gso)

        googleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            launcher.launch(signInIntent)
        }


        return binding.root
    }


    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    if (account != null) {
                        getGoogleAuthCredential(account)
                    }
                } else {
                    Toast.makeText(activity, task.exception.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun getGoogleAuthCredential(googleSignInAccount: GoogleSignInAccount) {
        val googleTokenId = googleSignInAccount.idToken
        val googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null)
        signInWithGoogleAuthCredential(googleAuthCredential)
    }

    private fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {
        userViewModel.signInWithGoogle(googleAuthCredential)
        userViewModel.currentUser.observe(
            this
        ) {
            if (it.isAuth) {
                redirectHome(it)
            } else {
                Toast.makeText(activity, "Failed to Sign in", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun redirectHome(user: User) {
        Redirect.goto(activity as Activity, HomeActivity(), user)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
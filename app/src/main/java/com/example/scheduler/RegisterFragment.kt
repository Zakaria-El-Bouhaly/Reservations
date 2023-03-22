package com.example.scheduler

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.scheduler.Models.RegisterDto
import com.example.scheduler.databinding.FrgmtRegisterBinding
import com.example.scheduler.viewModels.UserViewModel
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FrgmtRegisterBinding


    private lateinit var emailField: EditText
    private lateinit var psdfield: EditText
    private lateinit var psdconfrfield: EditText
    private lateinit var registerbtn: Button
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
        binding = FrgmtRegisterBinding.inflate(inflater)

        emailField = binding.emailfield
        psdfield = binding.password
        psdconfrfield = binding.password2
        registerbtn = binding.register

        userViewModel =
            ViewModelProvider(this, defaultViewModelProviderFactory)[UserViewModel::class.java]

        registerbtn.setOnClickListener {

            val username: String = emailField.text.toString()
            val pass: String = psdfield.text.toString()
            val passConf: String = psdconfrfield.text.toString()

            if (username != "" && pass != "" && passConf != "") {

                if (pass != passConf) {
                    val toast =
                        Toast.makeText(activity, "passwords don't match", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()

                } else {
                    val registerDto = RegisterDto(username, pass, passConf)

                    lifecycleScope.launch {

                        var result = userViewModel.register(registerDto).single()
                        if (result) {
                            Toast.makeText(activity, "User Registered..", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(activity, "Fail to register user..", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
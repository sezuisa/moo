package com.dhbw.heidenheim.haegele.moo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.registerButton.setOnClickListener {
            val email = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // registering

            CoroutineScope(Dispatchers.Main).launch {
                if (createAccount(email, password)) {
                    if (!isAdded) return@launch
                    activity?.runOnUiThread {
                        Toast.makeText(view.context, R.string.login_toast, Toast.LENGTH_SHORT)
                            .show()
                    }
                    it.findNavController().navigate(R.id.login_to_app)
                    (activity as LoginActivity).finish()
                } else
                    if (!isAdded) return@launch
                    activity?.runOnUiThread {
                        Toast.makeText(view.context, R.string.register_toast, Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }

        binding.loginButton.setOnClickListener{
            val email = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // logging in

            CoroutineScope(Dispatchers.Main).launch {
                if(login(email, password)){
                    if (!isAdded) return@launch
                    activity?.runOnUiThread {
                        Toast.makeText(view.context, R.string.hello, Toast.LENGTH_SHORT).show()
                    }
                    it.findNavController().navigate(R.id.login_to_app)
                    (activity as LoginActivity).finish()
                }else{
                    if (!isAdded) return@launch
                    activity?.runOnUiThread  {
                        Toast.makeText(view.context, R.string.app_name, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

}
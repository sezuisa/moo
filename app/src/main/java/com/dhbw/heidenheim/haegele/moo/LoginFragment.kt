package com.dhbw.heidenheim.haegele.moo

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.navigation.findNavController
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentLoginBinding
import com.google.android.material.color.MaterialColors
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

        binding.loginLogo.drawable.setTint(MaterialColors.getColor(requireContext(), com.google.android.material.R.attr.colorPrimary, Color.BLACK))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cardSettings = context?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val green = MooApp.res.getColor(R.color.ic_green, null)
        val yellow = MooApp.res.getColor(R.color.ic_yellow, null)
        val red = MooApp.res.getColor(R.color.ic_red, null)
        val grey = MooApp.res.getColor(R.color.grey, null)


        binding.registerButton.setOnClickListener {
            val email = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // registering

            CoroutineScope(Dispatchers.Main).launch {
                if (createAccount(email, password)) {
                    if (!isAdded) { return@launch }
                    activity?.runOnUiThread {
                        Toast.makeText(view.context, R.string.register_toast, Toast.LENGTH_SHORT)
                            .show()

                        Toast.makeText(view.context, R.string.requestToLogin_toast, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener{
            val email = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // logging in

            CoroutineScope(Dispatchers.Main).launch {
                if(login(email, password)){
                    if (!isAdded) { return@launch }
                    activity?.runOnUiThread {
                        Toast.makeText(view.context, R.string.login_toast, Toast.LENGTH_SHORT).show()
                        cardSettings?.edit {
                            putInt("happy_color", grey)
                            putInt("neutral_color", grey)
                            putInt("unhappy_color", grey)
                            putString("note","")
                            putString("highlight","")
                            commit()
                        }
                    }
                    it.findNavController().navigate(R.id.login_to_app)
                    (activity as LoginActivity).finish()
                }else{
                    if (!isAdded) { return@launch }
                    activity?.runOnUiThread  {
                        Toast.makeText(view.context, R.string.login_error_toast, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

}
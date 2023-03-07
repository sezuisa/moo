package com.dhbw.heidenheim.haegele.moo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dhbw.heidenheim.haegele.moo.databinding.ActivityLoginBinding
import com.mongodb.app.data.AuthRepository
import com.mongodb.app.data.RealmAuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.registerButton.setOnClickListener {
            val email = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            //register

            CoroutineScope(Dispatchers.IO).launch {

                if (createAccount(email, password)) {
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, R.string.hello, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, R.string.app_name, Toast.LENGTH_SHORT)
                            .show()
                    }
            }
            finish()
        }


        binding.loginButton.setOnClickListener{
            val email = binding.usernameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // login in

            CoroutineScope(Dispatchers.IO).launch {

                if(login(email, password)){
                    runOnUiThread{
                        Toast.makeText(this@LoginActivity, R.string.hello, Toast.LENGTH_SHORT).show()
                    }
                }else{

                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, R.string.app_name, Toast.LENGTH_SHORT).show()
                    }
                }

            }

            finish()
        }

        binding.exitButton.setOnClickListener {
            finish()
        }
    }
}
private val authRepository: AuthRepository = RealmAuthRepository
suspend fun login(email: String, password: String): Boolean {
    // Versuchen Sie, sich anzumelden
    return try {
        // Realm-Operationen ausführen

        authRepository.login(email, password)
        true // Erfolg zurückgeben
    } catch (ex: Throwable) {
        Log.e("TAG", "Failed to login: ${ex.message}")
        false // Misserfolg zurückgeben
    }
}
suspend fun createAccount(email: String, password: String): Boolean {
    // Versuchen Sie, den Benutzer zu registrieren
    return try {
        // Realm-Operationen ausführen
        authRepository.createAccount(email, password)
        true // Erfolg zurückgeben
    } catch (ex: Throwable) {
        Log.e("TAG", "Failed to create account: ${ex.message}")
        false // Misserfolg zurückgeben
    }
}

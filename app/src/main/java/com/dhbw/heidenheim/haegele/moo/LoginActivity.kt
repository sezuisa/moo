package com.dhbw.heidenheim.haegele.moo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
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

        val sp = PreferenceManager.getDefaultSharedPreferences(this)

        when (sp.getString("theme", "brown")) {
            "brown" -> setTheme(R.style.brownTheme)
            "bw" -> setTheme(R.style.bwTheme)
            "milka" -> setTheme(R.style.milkaTheme)
        }


        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.loginNavHost.id) as NavHostFragment
        val navController = navHostFragment.navController

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

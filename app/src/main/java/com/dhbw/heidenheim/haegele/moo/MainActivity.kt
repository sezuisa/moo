package com.dhbw.heidenheim.haegele.moo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.dhbw.heidenheim.haegele.moo.databinding.ActivityMainBinding
import androidx.navigation.fragment.NavHostFragment
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val syncRealmController = SyncRealmController()
    private val repository = syncRealmController.getRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.wrapperHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController


        binding.preferencesButton.setOnClickListener {
            Log.d("MOO-INFO", navController.currentDestination.toString())
            if (navController.currentDestination?.id == com.dhbw.heidenheim.haegele.moo.R.id.wrapperContentFragment) {
                navController.navigate(WrapperContentFragmentDirections.mainToPreferences())
            } else {
                navController.navigate(WrapperPreferencesFragmentDirections.preferencesToMain())
            }

        }

        binding.btnLogo.setOnClickListener {
            Log.d("TAG", "button Settings clicked!")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            val credentials =
                Credentials.emailPassword("test@1.de", "Test1234$")
            val user = app.login(credentials)
            Log.d("moo-INFO", "User is logged in: " + user.loggedIn)
        }
    }

}


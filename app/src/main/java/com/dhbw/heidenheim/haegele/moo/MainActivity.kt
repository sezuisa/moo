package com.dhbw.heidenheim.haegele.moo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.dhbw.heidenheim.haegele.moo.databinding.ActivityMainBinding
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val credentials = Credentials.emailPassword("sarah.haegele01@gmail.com", "testpassword123")
            val user = app.login(credentials)
            Log.d("moo-INFO", "User is logged in: " + user.loggedIn)
        }
    }
}
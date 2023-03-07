package com.dhbw.heidenheim.haegele.moo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.dhbw.heidenheim.haegele.moo.data.card.Happy
import com.dhbw.heidenheim.haegele.moo.data.card.MoodCard
import com.dhbw.heidenheim.haegele.moo.databinding.ActivityMainBinding
import com.mongodb.app.data.RealmSyncRepository
import com.mongodb.app.data.SyncRepository

import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

//    private val repository = RealmSyncRepository { _, error ->
//        // Sync errors come from a background thread so route the Toast through the UI thread
//        lifecycleScope.launch {
//            // Catch write permission errors and notify user. This is just a 2nd line of defense
//            // since we prevent users from modifying someone else's tasks
//            // TODO the SDK does not have an enum for this type of error yet so make sure to update this once it has been added
//
//        }
//    }
    private val syncRealmController = SyncRealmController()
    private val repository = syncRealmController.getRepo()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val credentials =
                Credentials.emailPassword("test@1.de", "Test1234$")
            val user = app.login(credentials)
            Log.d("moo-INFO", "User is logged in: " + user.loggedIn)
        }

        binding.icHappy.setOnClickListener {
            Log.d("TAG", "ImageView Happy clicked!")
            lifecycleScope.launch {
                repository.addCard("hier ist echt viel Staub","Staubsaugen","happy")

            }

        }
        binding.icUnhappy.setOnClickListener {
            Log.d("TAG", "ImageView Unhappy clicked!")
            lifecycleScope.launch {
                repository.addCard("hier ist echt viel Staub","Staubsaugen","unhappy")

            }
        }
        binding.icNeutral.setOnClickListener {
            Log.d("TAG", "ImageView Neutral clicked!")
            lifecycleScope.launch {
               repository.addCard("hier ist echt viel Staub","Staubsaugen","neutral")

            }
        }

        binding.btnAccount.setOnClickListener {
            Log.d("TAG", "button Settings clicked!")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }
}

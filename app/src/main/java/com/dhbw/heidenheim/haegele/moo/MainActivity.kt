package com.dhbw.heidenheim.haegele.moo

import android.R.drawable
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.dhbw.heidenheim.haegele.moo.databinding.ActivityMainBinding
import io.realm.kotlin.mongodb.Credentials
import kotlinx.coroutines.launch
import java.time.LocalDate


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
                binding.preferencesButton.icon = AppCompatResources.getDrawable(this, R.drawable.ic_rotating_settings)
                val icon = binding.preferencesButton.icon
                if (icon is AnimatedVectorDrawable) {
                    icon.start()
                }
            } else {
                navController.navigate(WrapperPreferencesFragmentDirections.preferencesToMain())
                binding.preferencesButton.icon = AppCompatResources.getDrawable(this, R.drawable.ic_settings)
            }

        }
    }

}


package com.dhbw.heidenheim.haegele.moo

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.PreferenceManager
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.dhbw.heidenheim.haegele.moo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val syncRealmController = SyncRealmController()
    private val repository = syncRealmController.getRepo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = PreferenceManager.getDefaultSharedPreferences(this)

        when (sp.getString("theme", "brown")) {
            "brown" -> setTheme(R.style.brownTheme)
            "bw" -> setTheme(R.style.bwTheme)
            "milka" -> setTheme(R.style.milkaTheme)
        }

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


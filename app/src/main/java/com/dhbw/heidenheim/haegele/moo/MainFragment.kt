package com.dhbw.heidenheim.haegele.moo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.dhbw.heidenheim.haegele.moo.databinding.ActivityMainBinding
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val syncRealmController = SyncRealmController()
        val repository = syncRealmController.getRepo()

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

        loadSettings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadSettings() {
        val sp = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val displayName = sp.getString("display_name", "User")

        binding.txtHello.text = getString(R.string.hello, displayName)
    }
}
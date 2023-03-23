package com.dhbw.heidenheim.haegele.moo

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.dhbw.heidenheim.haegele.moo.databinding.ActivityMainBinding
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

        //set the current Date to the TextView
        binding.dateOfCard.text = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy") )

        binding.icHappy.setOnClickListener {
            Log.d("TAG", "ImageView Happy clicked!")
            lifecycleScope.launch {
                repository.addCard("","","happy")

            }

        }
        binding.icUnhappy.setOnClickListener {
            Log.d("TAG", "ImageView Unhappy clicked!")
            lifecycleScope.launch {
                repository.addCard("","","unhappy")

            }
        }
        binding.icNeutral.setOnClickListener {
            Log.d("TAG", "ImageView Neutral clicked!")
            lifecycleScope.launch {
                repository.addCard("","","neutral")

            }
        }

        binding.mainHighlight.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val editTextHighlight = binding.mainHighlight.text.toString()
                Log.d("TAG", "Highlight Text Changed")
                lifecycleScope.launch {
                    repository.addCard("",editTextHighlight,"")

                }
                Toast.makeText(context, "Highlight added", Toast.LENGTH_SHORT).show()
            }
        }

        binding.txtFreetext.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val editFreeText = binding.txtFreetext.text.toString()
                Log.d("TAG", "Notes FreeText Changed")
                lifecycleScope.launch {
                    repository.addCard(editFreeText,"","")

                }
                Toast.makeText(context, "Note added", Toast.LENGTH_SHORT).show()
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
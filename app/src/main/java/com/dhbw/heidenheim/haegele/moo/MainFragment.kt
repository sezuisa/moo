package com.dhbw.heidenheim.haegele.moo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.dhbw.heidenheim.haegele.moo.data.SyncRealmController
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "prefUpdated") {
                // Aktualisiere die Ansicht mit den neuen Daten aus den SharedPreferences
                loadSettings()
            }
        }
    }



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

        val cardSettings = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val green = MooApp.res.getColor(R.color.ic_green, null)
        val yellow = MooApp.res.getColor(R.color.ic_yellow, null)
        val red = MooApp.res.getColor(R.color.ic_red, null)
        val grey = MooApp.res.getColor(R.color.grey, null)

        binding.icHappy.setOnClickListener {
            Log.d("TAG", "ImageView Happy clicked!")

            val animX = ObjectAnimator.ofFloat(it, "scaleX", 0.3f, 1.05f, 0.9f, 1f).setDuration(500)
            val animY = ObjectAnimator.ofFloat(it, "scaleY", 0.3f, 1.05f, 0.9f, 1f).setDuration(500)

            val colorAnimHappy = ObjectAnimator.ofArgb((it as ImageView).drawable, "tint", green)
                .setDuration(500)
            val colorAnimNeutral = ObjectAnimator.ofArgb(binding.icNeutral.drawable, "tint", grey)
                .setDuration(500)
            val colorAnimUnhappy = ObjectAnimator.ofArgb(binding.icUnhappy.drawable, "tint", grey)
                .setDuration(500)

            AnimatorSet().apply {
                playTogether(animX, animY, colorAnimHappy, colorAnimNeutral, colorAnimUnhappy)
                start()
            }
            lifecycleScope.launch {
                repository.addCard("","","happy")

            }
            cardSettings?.edit {
                putInt("happy_color", green)
                putInt("neutral_color", grey)
                putInt("unhappy_color", grey)
                commit()
            }

        }
        binding.icUnhappy.setOnClickListener {
            Log.d("TAG", "ImageView Unhappy clicked!")

            val animX = ObjectAnimator.ofFloat(it, "scaleX", 0.3f, 1.05f, 0.9f, 1f).setDuration(500)
            val animY = ObjectAnimator.ofFloat(it, "scaleY", 0.3f, 1.05f, 0.9f, 1f).setDuration(500)

            val colorAnimHappy = ObjectAnimator.ofArgb(binding.icUnhappy.drawable, "tint", grey)
                .setDuration(500)
            val colorAnimNeutral = ObjectAnimator.ofArgb(binding.icNeutral.drawable, "tint", grey)
                .setDuration(500)
            val colorAnimUnhappy = ObjectAnimator.ofArgb((it as ImageView).drawable, "tint", red)
                .setDuration(500)

            AnimatorSet().apply {
                playTogether(animX, animY, colorAnimHappy, colorAnimNeutral, colorAnimUnhappy)
                start()
            }
            lifecycleScope.launch {
                repository.addCard("","","unhappy")

            }
            cardSettings?.edit {
                putInt("happy_color", grey)
                putInt("neutral_color", grey)
                putInt("unhappy_color", red)
                commit()
            }
        }
        binding.icNeutral.setOnClickListener {
            Log.d("TAG", "ImageView Neutral clicked!")

            val animX = ObjectAnimator.ofFloat(it, "scaleX", 0.3f, 1.05f, 0.9f, 1f).setDuration(500)
            val animY = ObjectAnimator.ofFloat(it, "scaleY", 0.3f, 1.05f, 0.9f, 1f).setDuration(500)

            val colorAnimHappy = ObjectAnimator.ofArgb(binding.icHappy.drawable, "tint", grey)
                .setDuration(500)
            val colorAnimNeutral = ObjectAnimator.ofArgb((it as ImageView).drawable, "tint", yellow)
                .setDuration(500)
            val colorAnimUnhappy = ObjectAnimator.ofArgb(binding.icUnhappy.drawable, "tint", grey)
                .setDuration(500)

            AnimatorSet().apply {
                playTogether(animX, animY, colorAnimHappy, colorAnimNeutral, colorAnimUnhappy)
                start()
            }
            lifecycleScope.launch {
                repository.addCard("","","neutral")

            }
            cardSettings?.edit {
                putInt("happy_color", grey)
                putInt("neutral_color", yellow)
                putInt("unhappy_color", grey)
                commit()
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
                cardSettings?.edit {
                    putString("highlight", editTextHighlight)
                    commit()
                }
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
                cardSettings?.edit {
                    putString("note", editFreeText)
                    commit()
                }
            }
        }
        loadSettings()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadSettings() {
        val settings = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val displayName = settings.getString("display_name", "User")
        binding.txtHello.text = getString(R.string.hello, displayName)

        val cardSettings = activity?.getSharedPreferences("pref", Context.MODE_PRIVATE) ?: return

        val now = LocalDate.now()
        //set the current Date to the TextView
        binding.dateOfCard.text = now.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))

        // check if it's a new day - if it is, clear the contents of the card in home view
        var newDay = false
        val oldDay = cardSettings.getString("current_day", "not set")
        if (oldDay != null) {
            Log.d("MOO-INFO: Old day", oldDay)
        }
        if (oldDay.equals("not set") || now.isAfter(LocalDate.parse(oldDay))) {
            cardSettings.edit {
                putString("current_day", now.toString())
                commit()
            }
            newDay = true
        }

        Log.d("MOO-INFO: New day?", newDay.toString())

        val happyColor: Int
        val neutralColor: Int
        val unhappyColor: Int
        val highlight: String
        val note: String

        if (newDay) {
            happyColor = MooApp.res.getColor(R.color.grey, null)
            neutralColor = MooApp.res.getColor(R.color.grey, null)
            unhappyColor = MooApp.res.getColor(R.color.grey, null)
            highlight = ""
            note = ""
        } else {
            happyColor = cardSettings.getInt("happy_color", MooApp.res.getColor(R.color.grey, null))
            neutralColor = cardSettings.getInt("neutral_color", MooApp.res.getColor(R.color.grey, null))
            unhappyColor = cardSettings.getInt("unhappy_color", MooApp.res.getColor(R.color.grey, null))
            highlight = cardSettings.getString("note", "").toString()
            note = cardSettings.getString("highlight", "").toString()
        }

        binding.icHappy.drawable.setTint(happyColor)
        binding.icNeutral.drawable.setTint(neutralColor)
        binding.icUnhappy.drawable.setTint(unhappyColor)

        binding.mainHighlight.text = highlight.toEditable()
        binding.txtFreetext.text = note.toEditable()
    }

    private fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)

    override fun onResume() {
        super.onResume()
        // Registriere den BroadcastReceiver
        requireActivity().registerReceiver(broadcastReceiver, IntentFilter("prefUpdated"))
        // Aktualisiere die SharedPreferences
        loadSettings()
    }
}
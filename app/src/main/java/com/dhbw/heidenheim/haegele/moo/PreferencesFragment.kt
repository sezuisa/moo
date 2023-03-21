package com.dhbw.heidenheim.haegele.moo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceFragmentCompat
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentMainBinding
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentPreferencesBinding

class PreferencesFragment : PreferenceFragmentCompat() {

    private var _binding: FragmentPreferencesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

}
package com.dhbw.heidenheim.haegele.moo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentWrapperPreferencesBinding

class WrapperPreferencesFragment : Fragment() {

    private var _binding: FragmentWrapperPreferencesBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWrapperPreferencesBinding.inflate(inflater, container, false)

        val frag = PreferencesFragment()
        childFragmentManager
            .beginTransaction()
            .replace(binding.preferencesContent.id, frag)
            .commit()

        return binding.root
    }

}
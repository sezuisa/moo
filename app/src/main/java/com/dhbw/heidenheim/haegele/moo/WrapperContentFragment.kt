package com.dhbw.heidenheim.haegele.moo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dhbw.heidenheim.haegele.moo.databinding.FragmentWrapperContentBinding

class WrapperContentFragment : Fragment() {

    private var _binding: FragmentWrapperContentBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWrapperContentBinding.inflate(inflater, container, false)

        val navHost = NavHostFragment()
        childFragmentManager.beginTransaction().replace(binding.navHostContent.id, navHost).setPrimaryNavigationFragment(navHost).commitNow()
//        val navHostFragment =
//            childFragmentManager.findFragmentById(binding.navHostContent.id) as NavHostFragment
        navHost.navController.setGraph(R.navigation.nav_graph)
        val bottomNavigationView = binding.bottomNav
//        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navHost.navController)
        return binding.root
    }

}
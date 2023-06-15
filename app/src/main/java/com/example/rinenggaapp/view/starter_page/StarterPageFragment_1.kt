package com.example.rinenggaapp.view.starter_page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R


class StarterPageFragment_1 : Fragment() {

    private lateinit var skipButton: Button
    private lateinit var nextButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starter_page_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton = view.findViewById(R.id.next_button)
        skipButton = view.findViewById(R.id.skip_button)
        navController = Navigation.findNavController(view)

        skipButton.setOnClickListener {
            removeStarterPagefromFirstInstall()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }

        nextButton.setOnClickListener {
           navController.navigate(R.id.action_starterPageFragment_1_to_starterPageFragment_2)
        }
    }

    fun removeStarterPagefromFirstInstall() {
        val skipped = "SKIP"
        val sharedPreferences = activity?.getSharedPreferences("starterPage", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        editor?.putString("skipStarterPage", skipped)
        editor?.apply()


    }
}


package com.example.rinenggaapp.view.starter_page

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R


class StarterPageFragment_2 : Fragment() {

    private lateinit var skipButton : Button
    private lateinit var nextButton : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starter_page_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton = view.findViewById(R.id.next_button)
        skipButton = view.findViewById(R.id.skip_button)

        skipButton.setOnClickListener {
            removeStarterPagefromFirstInstall()
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }

        nextButton.setOnClickListener {
           Navigation.findNavController(requireView()).navigate(R.id.action_starterPageFragment_2_to_starterPageFragment_3)
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
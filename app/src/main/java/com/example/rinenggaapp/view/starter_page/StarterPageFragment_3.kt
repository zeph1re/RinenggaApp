package com.example.rinenggaapp.view.starter_page

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.rinenggaapp.MainActivity
import com.example.rinenggaapp.R
import com.example.rinenggaapp.view.auth.LoginActivity


class StarterPageFragment_3 : Fragment() {

    private lateinit var skipButton : Button
    private lateinit var nextButton : Button
    private lateinit var prefs : SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starter_page_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton = view.findViewById(R.id.next_button)
        skipButton = view.findViewById(R.id.skip_button)

        skipButton.setOnClickListener {
            removeStarterPagefromFirstInstall()
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }

        nextButton.setOnClickListener {
            removeStarterPagefromFirstInstall()
            startActivity(Intent(requireActivity(),LoginActivity::class.java))
        }

    }

    fun removeStarterPagefromFirstInstall() {
        val skipped = "SKIP"
        val sharedPreferences = activity?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        editor?.putString("SKIP", skipped)
        editor?.apply()
    }
}

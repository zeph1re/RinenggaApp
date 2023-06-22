package com.example.rinenggaapp.view.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rinenggaapp.R
import com.example.rinenggaapp.databinding.FragmentAssignmentDetailBinding


class AssignmentDetailFragment : Fragment() {

    private var _binding : FragmentAssignmentDetailBinding? = null
    val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_assignment_detail, container, false)
    }


}
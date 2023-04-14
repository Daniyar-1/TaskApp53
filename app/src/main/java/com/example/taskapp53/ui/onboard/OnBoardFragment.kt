package com.example.taskapp53.ui.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp53.R
import com.example.taskapp53.databinding.FragmentOnBoardBinding

class OnBoardFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)

        initViews()
        initListeners()

        return binding.root
    }

    private fun initListeners() {

    }

    private fun initViews() {
        val adapter = PageAdapter(childFragmentManager, this::onNextClick, this::onSkipClick)
        binding.vpBoard.adapter = adapter
        binding.dotsIndicator.attachTo(binding.vpBoard)

    }
    private fun onNextClick(){
        binding.vpBoard.currentItem += 1
    }

    private fun onSkipClick(){
        binding.vpBoard.currentItem = 2
    }
}
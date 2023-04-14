package com.example.taskapp53.ui.profile

import android.R.attr.previewImage
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.taskapp53.databinding.FragmentProfileBinding
import com.example.taskapp53.extensions.loadImage
import com.example.taskapp53.utils.Preferences


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    var mGetContent: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.GetContent()) { uri ->
        Log.e("ololo", ": $uri")

        binding.imgProfile.loadImage(uri.toString())

        Preferences(requireContext()).imgUri = uri.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        initViews()
        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.imgProfile.setOnClickListener{
            mGetContent.launch("image/*");
        }
    }

    private fun initViews() {
        binding.imgProfile.loadImage(Preferences(requireContext()).imgUri)
    }
}
package com.example.taskapp53.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp53.R
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.taskapp53.databinding.FragmentAuthBinding
import com.example.taskapp53.extensions.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class AuthFragment : Fragment() {

    private var auth = FirebaseAuth.getInstance()

    private lateinit var binding: FragmentAuthBinding
    private lateinit var correctCode: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)

        initViews()
        initListeners()

        return binding.root
    }

    private fun initListeners() {

    }

    private fun initViews() {

        binding.btnSend.setOnClickListener{
            if (binding.etPhone.text.isNotEmpty()){
                sendPhoneNumber()
            }else{
                binding.etPhone.error = "Поле пустое"
            }
        }

        binding.btnConfirm.setOnClickListener{
         sendCode()
        }
    }

    private fun sendPhoneNumber() {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(binding.etPhone.text.toString())       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d("ololo", "onVerificationCompleted:$credential")
                }

                override fun onVerificationFailed(firebaseException: FirebaseException) {
                    Log.e("ololo", "onVerificationFailed: " + firebaseException.message.toString())
                    showToast(firebaseException.message.toString())
                }

                override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, p1)

                    correctCode = verificationId

                    binding.etPhone.isVisible = false
                    binding.btnSend.isVisible = false

                    binding.etCode.isVisible = true
                    binding.btnConfirm.isVisible = true
                }
            })          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun sendCode(){
        val credential = PhoneAuthProvider.getCredential(correctCode, binding.etCode.text.toString())
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("ololo", "signInWithCredential:success")
                    showToast("Авторизация прошла успешно")
                    findNavController().navigate(R.id.navigation_home)
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w("ololo", "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        showToast("Verification code is incorrect")
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }
}
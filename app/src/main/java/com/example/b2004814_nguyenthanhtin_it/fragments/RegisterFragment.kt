package com.example.b2004814_nguyenthanhtin_it.fragments

import android.os.Bundle
import com.example.b2004814_nguyenthanhtin_it.apis.Constants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.b2004814_nguyenthanhtin_it.R
import com.example.b2004814_nguyenthanhtin_it.databinding.FragmentRegisterBinding
import com.example.b2004814_nguyenthanhtin_it.models.RequestRegisterOrLogin
import com.example.b2004814_nguyenthanhtin_it.sharedpreferences.AppSharedPreferences
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var mAppSharedPreferences: AppSharedPreferences
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        mAppSharedPreferences = AppSharedPreferences(requireContext())

        binding.apply {
            btnRegister.setOnClickListener {
                if(edtUsername.text.isNotEmpty()){
                    username = edtUsername.text.toString().trim()
                    registerUser(username)
                }else {
                    Snackbar.make(it, "Vui long nhap ma so sinh vien!" , Snackbar.LENGTH_LONG).show()
                }
            }
            tvLogin.setOnClickListener {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, LoginFragment())
                    .commit()
            }
        }
            return binding.root
    }

    private fun registerUser(userName: String){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main){
                    val response = Constants.getInstance().registerUser(RequestRegisterOrLogin(username))
                        .body()
                    if(response != null ){
                        mAppSharedPreferences.putIdUser("idUser", response.idUser!!)
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout,LoginFragment())
                            .commit()
                        progressBar.visibility = View.GONE
                    }else {
                        tvMessage.text =  response?.message
                        tvMessage.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }
}

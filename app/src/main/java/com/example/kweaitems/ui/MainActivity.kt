package com.example.kweaitems.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.kweaitems.R
import com.example.kweaitems.configs.Datafactory
import com.example.kweaitems.databinding.ActivityMainBinding
import com.example.kweaitems.models.LoginRequestModel
import com.example.kweaitems.models.RegisterResponseModel
import com.example.kweaitems.webservices.IExecute
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Response

/**
 * login users here
 */
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setClickListeners()
    }

    private fun setClickListeners() {
        try {
            binding.btnLogin.setOnClickListener { loginUser() }
            binding.twRegister.setOnClickListener { goToRegistrationScreen() }
        } catch (e : Exception) {
            Log.e(TAG, "setClickListeners: ", e)
        }
    }

    private fun goToRegistrationScreen() {
        try {
            startActivity( Intent(this, RegisterActivity::class.java))
        } catch (e : Exception) {
            Log.e(TAG, "goToRegistrationScreen: ", e)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loginUser() {
        try {
            if (valid()) {
                binding.btnLogin.text = "Please wait.."
                binding.btnLogin.isEnabled = false
                val request = LoginRequestModel(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
                Log.e(TAG, "loginUser: request: " + Gson().toJson(request))
                Datafactory.getKweaRepo().loginUser(
                    request,
                    object : IExecute<RegisterResponseModel?>{
                        override fun run(result: Response<RegisterResponseModel?>?, t: Throwable?) {
                            binding.btnLogin.text = "Login"
                            binding.btnLogin.isEnabled = true
                            try {
                                if (result != null) {
                                    val payload = result.body()
                                    Log.e( TAG, "loginUser: result: " + Gson().toJson(payload))
                                    if (payload != null) {
                                        if (!payload.token.isNullOrEmpty()) {
                                            Datafactory.setToken(
                                                payload.token
                                            )
                                            goToDisplayActivity()
                                        }  else {
                                            showError()
                                        }
                                    }  else {
                                        showError()
                                    }
                                } else {
                                    showError()
                                }
                            } catch (e : Exception) {
                                Log.e(TAG, "loginUser: ", e)
                                showError()
                            }
                        }
                    }
                )
            }
        } catch (e : Exception) {
            Log.e(TAG, "loginUser: ", e)
        }
    }

    private fun goToDisplayActivity() {
        try {
            startActivity(Intent(
                this, DisplayItemsActivity::class.java))
        } catch (e : Exception) {
            Log.e(TAG, "goToDisplayActivity: ", e)
        }
    }

    @SuppressLint("ShowToast")
    private fun showError() {
        try {
            Snackbar.make(binding.btnLogin,"Request failed", Snackbar.LENGTH_SHORT).show()
        } catch (e : Exception) {
            Log.e(TAG, "showError: ", e)
        }
    }

    private fun valid(): Boolean {
        var allGood = true
        try {
            if (binding.etEmail.text.toString() == "") {
                binding.etEmail.error = "Please provide email"
                allGood = false
            }
            if (binding.etPassword.text.toString() == "") {
                binding.etPassword.error = "Please provide password"
                allGood = false
            }
        } catch (e : Exception) {
            Log.e(TAG, "valid: ", e)
        }
        return allGood
    }
}
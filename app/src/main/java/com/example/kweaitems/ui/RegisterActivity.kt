package com.example.kweaitems.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.kweaitems.R
import com.example.kweaitems.configs.Datafactory
import com.example.kweaitems.databinding.ActivityRegisterBinding
import com.example.kweaitems.models.LoginRequestModel
import com.example.kweaitems.models.RegisterRequestModel
import com.example.kweaitems.models.RegisterResponseModel
import com.example.kweaitems.webservices.IExecute
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"

    private lateinit var binding : ActivityRegisterBinding

    private var gender = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        setClickListeners()
    }

    private fun setClickListeners() {
        try {
            binding.btnRegister.setOnClickListener { registerUser() }
            binding.twLogin.setOnClickListener { super.onBackPressed() }
            binding.rbMale.setOnCheckedChangeListener { _, b ->
                if (b) {
                    gender = 1
                }
            }
            binding.rbFemale.setOnCheckedChangeListener { _, b ->
                if (b) {
                    gender = 2
                }
            }
        } catch (e : Exception) {
            Log.e(TAG, "setClickListeners: ", e)
        }
    }

    private fun registerUser() {
        try {
            if (valid()) {
                binding.btnRegister.text = "Please wait.."
                binding.btnRegister.isEnabled = false
                val request = RegisterRequestModel(
                    binding.etName.text.toString(),
                    binding.etOtherName.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etConfirmPassword.text.toString(),
                    binding.etPhoneNumber.text.toString(),
                    gender,
                    binding.etDob.text.toString(),
                    binding.etCountry.text.toString(),
                    binding.etCity.text.toString(),
                    binding.etState.text.toString(),
                    binding.etPostalCode.text.toString(),
                    binding.etAddress.text.toString(),
                    binding.etIncome.text.toString(),
                    binding.etOccupation.text.toString()
                )
                Log.e(TAG, "registerUser: request: " + Gson().toJson(request))
                Datafactory.getKweaRepo().registerUser(
                    request,
                    object : IExecute<RegisterResponseModel?> {
                        override fun run(result: Response<RegisterResponseModel?>?, t: Throwable?) {
                            binding.btnRegister.text = "Register"
                            binding.btnRegister.isEnabled = true
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
            finish()
        } catch (e : Exception) {
            Log.e(TAG, "goToDisplayActivity: ", e)
        }
    }

    @SuppressLint("ShowToast")
    private fun showError() {
        try {
            Snackbar.make(binding.btnRegister,"Request failed", Snackbar.LENGTH_SHORT).show()
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
            if (binding.etConfirmPassword.text.toString() == "") {
                binding.etConfirmPassword.error = "Please provide confirmation password"
                allGood = false
            }
            if (binding.etConfirmPassword.text.toString() !=
                binding.etPassword.text.toString()) {
                binding.etConfirmPassword.error = "Passwords do not match"
                allGood = false
            }
            if (binding.etName.text.toString() == "") {
                binding.etName.error = "Please provide name"
                allGood = false
            }
            if (binding.etPhoneNumber.text.toString() == "") {
                binding.etPhoneNumber.error = "Please provide phone number"
                allGood = false
            }
            if (binding.etDob.text.toString() == "") {
                binding.etDob.error = "Please provide dob"
                allGood = false
            }
            if (binding.etCity.text.toString() == "") {
                binding.etCity.error = "Please provide city"
                allGood = false
            }
            if (binding.etState.text.toString() == "") {
                binding.etState.error = "Please provide state"
                allGood = false
            }
            if (binding.etPostalCode.text.toString() == "") {
                binding.etPostalCode.error = "Please provide postal code"
                allGood = false
            }
            if (binding.etAddress.text.toString() == "") {
                binding.etAddress.error = "Please provide address"
                allGood = false
            }
            if (binding.etIncome.text.toString() == "") {
                binding.etIncome.error = "Please provide income"
                allGood = false
            }
            if (binding.etOccupation.text.toString() == "") {
                binding.etOccupation.error = "Please provide occupation"
                allGood = false
            }
            if (binding.etCountry.text.toString() == "") {
                binding.etCountry.error = "Please provide country"
                allGood = false
            }
        } catch (e : Exception) {
            Log.e(TAG, "valid: ", e)
        }
        return allGood
    }
}
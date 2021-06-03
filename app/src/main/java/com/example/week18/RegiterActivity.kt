package com.example.week18

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.week18.databinding.ActivityRegiterBinding
import com.example.week18.model.User
import com.example.week18.response.SingleResponse
import com.example.week18.webservice.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegiterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegiterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegiterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        btnRegister()
    }

    private fun register(){
        val name = binding.etName.text.toString()
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        ApiService.ApiEndPoint().register(name, username, email, password).enqueue(object : Callback<SingleResponse<User>>{
            override fun onResponse(call: Call<SingleResponse<User>>, response: Response<SingleResponse<User>>) {
                if (response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        Toast.makeText(applicationContext, "Register Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegiterActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<SingleResponse<User>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    private fun btnRegister(){
        binding.btnRegister.setOnClickListener {
            register()
        }
    }
}
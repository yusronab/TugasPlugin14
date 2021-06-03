package com.example.week18

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.week18.databinding.ActivityMainBinding
import com.example.week18.model.User
import com.example.week18.response.SingleResponse
import com.example.week18.webservice.ApiService
import com.example.week18.webservice.Constant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toRegister()
        btnLogin()
    }

    override fun onResume() {
        super.onResume()
        isLogin()
    }

    private fun toRegister(){
        binding.layoutToRegister.setOnClickListener {
            startActivity(Intent(this, RegiterActivity::class.java))
            finish()
        }
    }

    private fun isLogin(){
        val token = Constant.getToken(this)
        if (!token.equals("Undef")){
            startActivity(Intent(this, HomeActivity::class.java)).also { finish() }
        }
    }

    private fun doLogin(){
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()
        if (username.isEmpty() && password.isEmpty()){
            Toast.makeText(applicationContext, "Username or Password cannot empty", Toast.LENGTH_SHORT).show()
        }else{
            ApiService.ApiEndPoint().login(username, password).enqueue(object : Callback<SingleResponse<User>>{
                override fun onResponse(call: Call<SingleResponse<User>>, response: Response<SingleResponse<User>>) {
                    if (response.isSuccessful){
                        val body = response.body()
                        if (body != null){
                            Constant.setToken(this@MainActivity, body.data.token)
                            Toast.makeText(applicationContext, "Welcome ${body.data.name}", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                            finish()
                        }
                    }
                }

                override fun onFailure(call: Call<SingleResponse<User>>, t: Throwable) {
                    println(t.message)
                }
            })
        }
    }

    private fun btnLogin(){
        binding.btnLogin.setOnClickListener {
            doLogin()
        }
    }
}
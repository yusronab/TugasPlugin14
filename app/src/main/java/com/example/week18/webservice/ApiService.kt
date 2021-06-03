package com.example.week18.webservice

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {
    companion object{
        private var retrofit: Retrofit? = null
        private var okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        private fun getClient(): Retrofit{
            return if (retrofit == null){
                retrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()).build()
                retrofit!!
            }else{
                retrofit!!
            }
        }

        fun ApiEndPoint(): ApiEndPoint = getClient().create(ApiEndPoint::class.java)
    }
}

class Constant{
    companion object{
        const val BASE_URL = "https://apibarang.herokuapp.com/"

        fun setToken(context: Context, token: String){
            val sharedPreference = context.getSharedPreferences("Token", Context.MODE_PRIVATE)
            sharedPreference.edit().apply {
                putString("Token", token)
                apply()
            }
        }

        fun getToken(context: Context): String{
            val sharedPref = context.getSharedPreferences("Token", Context.MODE_PRIVATE)
            val token = sharedPref.getString("Token", "Undef")
            return token!!
        }

        fun clearToken(context: Context){
            val sharedPref = context.getSharedPreferences("Token", Context.MODE_PRIVATE)
            sharedPref.edit().clear().apply()
        }
    }
}
package com.example.kweaitems.repositories

import android.util.Log
import com.example.kweaitems.configs.AppConfig
import com.example.kweaitems.models.ItemsResponseModel
import com.example.kweaitems.models.LoginRequestModel
import com.example.kweaitems.models.RegisterRequestModel
import com.example.kweaitems.models.RegisterResponseModel
import com.example.kweaitems.webservices.IExecute
import com.example.kweaitems.webservices.KweaWebService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class KweaRepo {
    private val TAG = "KweaRepo"

    /** The web services.  */
    private var webservice: KweaWebService

    /** Request Timeouts  */
    var okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(AppConfig.REQUEST_TIMEOUT_CONNECT.toLong(), TimeUnit.SECONDS)
        .readTimeout(AppConfig.REQUEST_TIMEOUT_READ.toLong(), TimeUnit.SECONDS)
        .writeTimeout(AppConfig.REQUEST_TIMEOUT_WRITE.toLong(), TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(TokenInterceptor())
        .build()



    init {
        val retro = Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        webservice = retro.create(KweaWebService::class.java)
    }

    /**
     * method to connect to the register endpoint
     */
    fun registerUser(requestModel: RegisterRequestModel?,
                  callback: IExecute<RegisterResponseModel?>
    ) {
        webservice.registerUser(requestModel)?.enqueue(object :
            Callback<RegisterResponseModel?> {
            override fun onResponse(call: Call<RegisterResponseModel?>, response: Response<RegisterResponseModel?>) {
                callback.run(response, null)
            }

            override fun onFailure(call: Call<RegisterResponseModel?>, t: Throwable) {
                callback.run(null, t)
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }

    /**
     * method to connect to the login endpoint
     */
    fun loginUser(
        requestModel: LoginRequestModel?,
        callback: IExecute<RegisterResponseModel?>
    ) {
        webservice.loginUser(requestModel)?.enqueue(object :
            Callback<RegisterResponseModel?> {
            override fun onResponse(call: Call<RegisterResponseModel?>, response: Response<RegisterResponseModel?>) {
                callback.run(response, null)
            }

            override fun onFailure(call: Call<RegisterResponseModel?>, t: Throwable) {
                callback.run(null, t)
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }

    /**
     * method to connect to the fetch items endpoint
     */
    fun fetchKweaItems(
                  callback: IExecute<ItemsResponseModel?>
    ) {
        webservice.fetchKweaItems()?.enqueue(object :
            Callback<ItemsResponseModel?> {
            override fun onResponse(call: Call<ItemsResponseModel?>, response: Response<ItemsResponseModel?>) {
                callback.run(response, null)
            }

            override fun onFailure(call: Call<ItemsResponseModel?>, t: Throwable) {
                callback.run(null, t)
                Log.e(TAG, "onFailure: ", t)
            }
        })
    }

}
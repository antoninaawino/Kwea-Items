package com.example.kweaitems.webservices

import com.example.kweaitems.models.ItemsResponseModel
import com.example.kweaitems.models.LoginRequestModel
import com.example.kweaitems.models.RegisterRequestModel
import com.example.kweaitems.models.RegisterResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface KweaWebService {

    /**
     * endpoint to register user
     */
    @POST("register")
    fun registerUser(
        @Body request: RegisterRequestModel?
    ): Call<RegisterResponseModel?>?

    /**
     * endpoint to login user
     */
    @POST("login")
    fun loginUser(
        @Body request: LoginRequestModel?
    ): Call<RegisterResponseModel?>?

    /**
     * endpoint to fetch kwea items
     */
    @GET("kwea-items")
    fun fetchKweaItems(): Call<ItemsResponseModel?>?

}
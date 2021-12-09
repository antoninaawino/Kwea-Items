package com.example.kweaitems.repositories;


import com.example.kweaitems.configs.Datafactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        //rewrite the request to add bearer token
        Request newRequest=chain.request().newBuilder()
                .header("Authorization","Bearer "+ Datafactory.getToken())
                .build();

        return chain.proceed(newRequest);
    }
}
package com.example.kweaitems.models

data class LoginRequestModel (
    var email : String ?= null,
    var password : String ?= null,
)
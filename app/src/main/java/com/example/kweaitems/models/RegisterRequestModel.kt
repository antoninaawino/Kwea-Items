package com.example.kweaitems.models

data class RegisterRequestModel(
    var name : String ?= null,
    var other_names : String ?= null,
    var email : String ?= null,
    var password : String ?= null,
    var password_confirmation : String ?= null,
    var msisdn : String ?= null,
    var gender : Int ?= null,
    var dob : String ?= null,
    var country : String ?= null,
    var city : String ?= null,
    var state : String ?= null,
    var postal_code : String ?= null,
    var address : String ?= null,
    var income : String ?= null,
    var occupation : String ?= null
)

data class RegisterResponseModel(
    var token : String ?= null
)

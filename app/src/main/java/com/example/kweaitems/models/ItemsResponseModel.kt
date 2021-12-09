package com.example.kweaitems.models

data class ItemsResponseModel(
    var data : MutableList<ItemsDataModel> ?= null
)

data class ItemsDataModel (
    var item_name : String ?= null,
    var item_price : Int ?= null,
    var item_description : String ?= null,
    var item_category : String ?= null,
    var item_currency : String ?= null,
    var shop : ItemShopModel ?= null,
    var images : MutableList<ItemsImagesModel> ?= null
)

data class ItemShopModel (
    var shop_name : String ?= null,
    var shop_email : String ?= null,
    var shop_msisdn : String ?= null,
    var shop_address : String ?= null
)

data class ItemsImagesModel (
    var image : String ?= null
)

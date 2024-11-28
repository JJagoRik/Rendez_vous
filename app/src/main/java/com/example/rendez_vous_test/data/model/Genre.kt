package com.example.rendez_vous_test.data.model

import com.google.gson.annotations.SerializedName

data class Genre (
    @SerializedName("id"   ) var id   : Int?    = null,
    @SerializedName("name" ) var name : String? = null
)
package com.example.rendez_vous_test.data.model

import com.google.gson.annotations.SerializedName

data class GenresList(
    @SerializedName("genres" ) var genres : ArrayList<Genre> = arrayListOf()
)
package com.example.b2004814_nguyenthanhtin_it.models

import com.google.gson.annotations.SerializedName

data class RequestUpdateWish(
    val idUser: String,
    val idWish: String,
    @SerializedName("name")
    val fullName: String,
    val content: String
)

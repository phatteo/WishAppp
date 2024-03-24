package com.example.b2004814_nguyenthanhtin_it.models

import com.google.gson.annotations.SerializedName

data class Wish(
    @SerializedName("_id")
    val _id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("owner")
    val owner:User,
    @SerializedName("content")
    val content: String
)

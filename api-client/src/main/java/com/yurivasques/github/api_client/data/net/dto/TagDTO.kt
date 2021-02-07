package com.yurivasques.github.api_client.data.net.dto

import com.google.gson.annotations.SerializedName

class TagDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
)
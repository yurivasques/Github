package com.yurivasques.github.api_client.data.net.dto

import com.google.gson.annotations.SerializedName

class RepoDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String
)
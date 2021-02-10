package com.yurivasques.github.api_client.data.net.dto

import com.google.gson.annotations.SerializedName

class TagDTO(
    @SerializedName("node_id") val id: String,
    @SerializedName("name") val name: String
)
package com.github.yurivasques.github_library.data.net.dto

import com.google.gson.annotations.SerializedName

class TagDTO(
    @SerializedName("node_id") val id: String,
    @SerializedName("name") val name: String
)
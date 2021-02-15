package com.github.yurivasques.github_library.data.net.dto

import com.google.gson.annotations.SerializedName

class RepoDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String
)
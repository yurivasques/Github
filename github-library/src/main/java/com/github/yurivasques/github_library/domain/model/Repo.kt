package com.github.yurivasques.github_library.domain.model

data class Repo (
    val id: Long,
    val name: String,
    val description: String?,
    val userName: String
)
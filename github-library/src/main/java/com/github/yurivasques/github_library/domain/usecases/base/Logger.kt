package com.github.yurivasques.github_library.domain.usecases.base

interface Logger {

    fun log(message: () -> String)
    fun logError(throwable: () -> Throwable)

}
package com.yurivasques.github.api_client.domain.usecases.base

interface Logger {
    fun log(message: () -> String)
    fun logError(throwable: () -> Throwable)
}
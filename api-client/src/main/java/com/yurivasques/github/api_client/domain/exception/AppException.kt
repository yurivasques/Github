package com.yurivasques.github.api_client.domain.exception

sealed class AppException(message: String) : RuntimeException(message)

object NoConnectionException : AppException("Sem conexão com a internet")

object PersistenceException : AppException("Ocorreu um erro ao persistir os dados")
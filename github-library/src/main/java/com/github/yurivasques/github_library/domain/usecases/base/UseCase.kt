package com.github.yurivasques.github_library.domain.usecases.base

/**
 * Base Use Case
 */
abstract class UseCase<R, in P>(private val logger: Logger?) {

    /**
     * Builds which will be used when executing the current [UseCase].
     */
    protected abstract fun build(param: P): R

    /**
     * Executes the current use case.
     */
    fun execute(param: P): R = execute(param, false)

    protected open fun execute(param: P, fromUseCase: Boolean): R {
        logger?.log { "${javaClass.simpleName} : $param" }
        return build(param)
    }

}
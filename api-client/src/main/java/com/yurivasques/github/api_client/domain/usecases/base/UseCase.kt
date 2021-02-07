package com.yurivasques.github.api_client.domain.usecases.base

abstract class UseCase<R, in P>(private val logger: Logger?) {

    /**
     * Builds which will be used when executing the current [UseCase].
     */
    protected abstract fun build(param: P): R

    /**
     * Executes the current use case.
     */
    fun execute(param: P): R = execute(param, false)

    /**
     * To not apply transformer with [UseCaseScheduler]
     * This method can be used just in domain module (internal).
     */
    internal fun executeFromAnOtherUseCase(param: P): R = execute(param, true)

    protected open fun execute(param: P, fromUseCase: Boolean): R {
        logger?.log { "${javaClass.simpleName} : $param" }
        return build(param)
    }

}
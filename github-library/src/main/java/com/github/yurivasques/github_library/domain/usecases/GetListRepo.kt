package com.github.yurivasques.github_library.domain.usecases

import com.github.yurivasques.github_library.domain.exception.NoConnectionException
import com.github.yurivasques.github_library.domain.functions.StatementSingle
import com.github.yurivasques.github_library.domain.model.Repo
import com.github.yurivasques.github_library.domain.repository.RepoRepository
import com.github.yurivasques.github_library.domain.usecases.base.Logger
import com.github.yurivasques.github_library.domain.usecases.base.SingleUseCase
import com.github.yurivasques.github_library.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * This class is an implementation of [SingleUseCase] that represents a use case for
 * retrieving a collection of all user's [Repo] through [RepoRepository].
 * It is the external access class to retrieve a list of repositories
 * of a given username from both GitHub API and local cache
 */
class GetListRepo
@Inject internal constructor(
    private val repoRepository: RepoRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<List<Repo>, String>(useCaseScheduler, logger) {

    /**
     * Builds the use case process.
     * Defines a Single that gets a list of [Repo] from the local cache
     * and the Single that gets a list of [Repo] from GitHub API.
     * The Single to be executed is defined by the status of the network connection.
     * If the connection is up, it uses the emission from GitHub
     * otherwise uses the emission from cache.
     * In case of cache emission empty, returns No Connection Exception
     * @param param repositories owner's username to search
     * @return emission with a list of [Repo]
     * @throws [NoConnectionException]
     * @see [StatementSingle.ifThen]
     * @see [repoRepository]
     */
    override fun build(param: String): Single<List<Repo>> {
        val getCacheListRepo = repoRepository.getCacheListRepo(param)

        val cacheSingle = getCacheListRepo
            .map { if (it.isEmpty()) throw NoConnectionException else it }

        val netSingle = repoRepository.getListRepo(param)
            .flatMap { repoRepository.saveListRepo(it).andThen(getCacheListRepo) }

        return StatementSingle.ifThen(repoRepository.isConnected, netSingle, cacheSingle)
    }

}

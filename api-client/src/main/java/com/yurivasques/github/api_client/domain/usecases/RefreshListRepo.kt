package com.yurivasques.github.api_client.domain.usecases

import com.yurivasques.github.api_client.domain.functions.ConnectionFilter
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import com.yurivasques.github.api_client.domain.usecases.base.Logger
import com.yurivasques.github.api_client.domain.usecases.base.SingleUseCase
import com.yurivasques.github.api_client.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * This class is an implementation of [SingleUseCase] that represents a use case for
 * retrieving a collection of all user's [Repo] through [RepoRepository].
 * It is the external access class to retrieve a list of repositories
 * of a given username from both GitHub API and local cache
 */
class RefreshListRepo
@Inject internal constructor(
    private val repoRepository: RepoRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<List<Repo>, String>(useCaseScheduler, logger) {

    /**
     * Builds the use case process.
     * It gets the list of [Repo] from GitHub API, saves to local cache
     * and returns the emission from a get to the local cache
     * If the connection is down, returns No Connection Exception.
     * @param param repositories owner's username to search
     * @return emission with a list of [Repo]
     * @see [ConnectionFilter]
     * @see [RepoRepository]
     */
    override fun build(param: String): Single<List<Repo>> =
        Single.just(repoRepository.isConnected)
            .filter(ConnectionFilter())
            .flatMapSingle { _ ->
                repoRepository.getListRepo(param)
                    .flatMap {
                        repoRepository.saveListRepo(it)
                            .andThen(repoRepository.getCacheListRepo(param))
                    }
            }.toSingle()


}

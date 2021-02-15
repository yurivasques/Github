package com.github.yurivasques.github_library.domain.usecases

import com.github.yurivasques.github_library.domain.functions.ConnectionFilter
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

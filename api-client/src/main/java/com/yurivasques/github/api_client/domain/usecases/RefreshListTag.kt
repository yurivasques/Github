package com.yurivasques.github.api_client.domain.usecases

import com.yurivasques.github.api_client.domain.functions.ConnectionFilter
import com.yurivasques.github.api_client.domain.model.Tag
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import com.yurivasques.github.api_client.domain.repository.TagRepository
import com.yurivasques.github.api_client.domain.usecases.base.Logger
import com.yurivasques.github.api_client.domain.usecases.base.SingleUseCase
import com.yurivasques.github.api_client.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * This class is an implementation of [SingleUseCase] that represents a use case for
 * retrieving a collection of all repository's [Tag] through [TagRepository].
 * It is the external access class to retrieve a list of tags
 * of a given username and repository from both GitHub API and local cache.
 */
class RefreshListTag
@Inject internal constructor(
    private val tagRepository: TagRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<List<Tag>, RefreshListTag.Param>(useCaseScheduler, logger) {

    /**
     * Builds the use case process.
     * Defines a Single that gets a list of [Tag] from GitHub API, saves to local cache
     * and returns the emission from a get to the local cache
     * If the connection is down, returns No Connection Exception.
     * @param param instance of [Param] with search parameters
     * @return emission with a list of [Tag]
     * @see [ConnectionFilter]
     * @see [TagRepository]
     */
    override fun build(param: Param): Single<List<Tag>> =
        Single.just(tagRepository.isConnected)
            .filter(ConnectionFilter())
            .flatMapSingle { _ ->
                tagRepository.getListTag(param.userName, param.repoName, param.repoId)
                    .flatMap {
                        tagRepository.saveListTag(it)
                            .andThen(tagRepository.getCacheListTag(param.repoId))
                    }
            }.toSingle()

    /**
     * Data class to specify the three used parameters of [RefreshListTag]
     * @param userName repositories owner's username to search
     * @param repoName repository's name to search
     * @param repoId repository's id to persist
     */
    data class Param(val userName: String, val repoName: String, val repoId: Long)
}

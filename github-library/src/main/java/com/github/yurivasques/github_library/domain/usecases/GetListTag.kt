package com.github.yurivasques.github_library.domain.usecases

import com.github.yurivasques.github_library.domain.exception.NoConnectionException
import com.github.yurivasques.github_library.domain.functions.StatementSingle
import com.github.yurivasques.github_library.domain.model.Tag
import com.github.yurivasques.github_library.domain.repository.TagRepository
import com.github.yurivasques.github_library.domain.usecases.base.Logger
import com.github.yurivasques.github_library.domain.usecases.base.SingleUseCase
import com.github.yurivasques.github_library.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * This class is an implementation of [SingleUseCase] that represents a use case for
 * retrieving a collection of all repository's [Tag] through [TagRepository].
 * It is the external access class to retrieve a list of tags
 * of a given username and repository from both GitHub API and local cache
 */
class GetListTag
@Inject internal constructor(
    private val tagRepository: TagRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<List<Tag>, GetListTag.Param>(useCaseScheduler, logger) {

    /**
     * Builds the use case process.
     * Defines a Single that gets a list of [Tag] from the local cache
     * and the Single that gets a list of [Tag] from GitHub API.
     * The Single to be executed is defined by the status of the network connection.
     * If the connection is up, it uses the emission from GitHub
     * otherwise uses the emission from cache.
     * In case of cache emission empty, returns No Connection Exception
     * @param param repositories owner's username to search
     * @return emission with a list of [Tag]
     * @throws [NoConnectionException]
     * @see [StatementSingle.ifThen]
     * @see [TagRepository]
     */
    override fun build(param: Param): Single<List<Tag>> {
        val getCacheListTag = tagRepository.getCacheListTag(param.repoId)

        val cacheSingle = getCacheListTag
            .map { if (it.isEmpty()) throw NoConnectionException else it }

        val netSingle = tagRepository.getListTag(param.userName, param.repoName, param.repoId)
            .flatMap { tagRepository.saveListTag(it).andThen(getCacheListTag) }

        return StatementSingle.ifThen(tagRepository.isConnected, netSingle, cacheSingle)
    }

    /**
     * Data class to specify the three used parameters of [GetListTag]
     * @param userName repositories owner's username to search
     * @param repoName repository's name to search
     * @param repoId repository's id to persist
     */
    data class Param(val userName: String, val repoName: String, val repoId: Long)

}

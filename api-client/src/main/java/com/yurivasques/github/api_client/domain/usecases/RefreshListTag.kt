package com.yurivasques.github.api_client.domain.usecases

import com.yurivasques.github.api_client.domain.functions.ConnectionFilter
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.model.Tag
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import com.yurivasques.github.api_client.domain.repository.TagRepository
import com.yurivasques.github.api_client.domain.usecases.base.Logger
import com.yurivasques.github.api_client.domain.usecases.base.SingleUseCase
import com.yurivasques.github.api_client.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RefreshListTag
@Inject internal constructor(
    private val tagRepository: TagRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<List<Tag>, RefreshListTag.Param>(useCaseScheduler, logger) {

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

    data class Param(val userName: String, val repoName: String, val repoId: Long)
}

package com.yurivasques.github.api_client.domain.usecases

import com.yurivasques.github.api_client.domain.exception.NoConnectedException
import com.yurivasques.github.api_client.domain.functions.StatementSingle
import com.yurivasques.github.api_client.domain.model.Tag
import com.yurivasques.github.api_client.domain.repository.TagRepository
import com.yurivasques.github.api_client.domain.usecases.base.Logger
import com.yurivasques.github.api_client.domain.usecases.base.SingleUseCase
import com.yurivasques.github.api_client.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetListTag
@Inject internal constructor(
    private val tagRepository: TagRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<List<Tag>, GetListTag.Param>(useCaseScheduler, logger) {

    override fun build(param: Param): Single<List<Tag>> {
        val getCacheListTag = tagRepository.getCacheListTag(param.repoId)

        val cacheSingle = getCacheListTag
            .map { if (it.isEmpty()) throw NoConnectedException else it }

        val netSingle = tagRepository.getListTag(param.userName, param.repoName, param.repoId)
            .flatMap {
                tagRepository.saveListTag(it).andThen(getCacheListTag)
            }

        return StatementSingle.ifThen(tagRepository.isConnected, netSingle, cacheSingle)
    }

    data class Param(val userName: String, val repoName: String, val repoId: Long)

}

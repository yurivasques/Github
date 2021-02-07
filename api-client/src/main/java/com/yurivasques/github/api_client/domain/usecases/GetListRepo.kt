package com.yurivasques.github.api_client.domain.usecases

import com.yurivasques.github.api_client.domain.exception.NoConnectedException
import com.yurivasques.github.api_client.domain.functions.StatementSingle
import com.yurivasques.github.api_client.domain.model.Repo
import com.yurivasques.github.api_client.domain.repository.RepoRepository
import com.yurivasques.github.api_client.domain.usecases.base.Logger
import com.yurivasques.github.api_client.domain.usecases.base.SingleUseCase
import com.yurivasques.github.api_client.domain.usecases.base.UseCaseScheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetListRepo
@Inject internal constructor(
    private val repoRepository: RepoRepository,
    useCaseScheduler: UseCaseScheduler? = null, logger: Logger? = null
) : SingleUseCase<List<Repo>, String>(useCaseScheduler, logger) {

    override fun build(param: String): Single<List<Repo>> {
        val getCacheListRepo = repoRepository.getCacheListRepo(param)

        val cacheSingle = getCacheListRepo
            .map { if (it.isEmpty()) throw NoConnectedException else it }

        val netSingle = repoRepository.getListRepo(param)
            .flatMap { repoRepository.saveListRepo(it).andThen(getCacheListRepo) }

        return StatementSingle.ifThen(repoRepository.isConnected, netSingle, cacheSingle)
    }

}

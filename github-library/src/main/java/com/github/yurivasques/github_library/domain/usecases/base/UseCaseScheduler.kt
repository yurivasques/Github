package com.github.yurivasques.github_library.domain.usecases.base

import io.reactivex.rxjava3.core.Scheduler

data class UseCaseScheduler(val run: Scheduler, val post: Scheduler)
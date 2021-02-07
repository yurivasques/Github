package com.yurivasques.github.api_client.data.net

import com.yurivasques.github.api_client.data.net.dto.RepoDTO
import com.yurivasques.github.api_client.data.net.dto.TagDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {
    @GET("users/{userName}/repos")
    fun getListRepos(@Path("userName") userName: String): Single<List<RepoDTO>>

    @GET("/repos/{userName}/{repoName}/tags")
    fun getListTags(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): Single<List<TagDTO>>
}
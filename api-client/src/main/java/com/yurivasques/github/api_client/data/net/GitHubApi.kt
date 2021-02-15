package com.yurivasques.github.api_client.data.net

import com.yurivasques.github.api_client.data.net.dto.RepoDTO
import com.yurivasques.github.api_client.data.net.dto.TagDTO
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import com.yurivasques.github.api_client.domain.model.Tag
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface used to make request for GitHub API
 */
interface GitHubApi {

    /**
     * Get a list of [RepoDTO] from GitHub API
     * @param userName GitHub's username
     * @return list of [RepoDTO]
     */
    @GET("users/{userName}/repos")
    fun getListRepos(@Path("userName") userName: String): Single<List<RepoDTO>>

    /**
     * Get a list of [RepoDTO] from GitHub API
     * @param userName GitHub's username
     * @param repoName GitHub's repository name
     * @return list of [RepoDTO]
     */
    @GET("/repos/{userName}/{repoName}/tags")
    fun getListTags(
        @Path("userName") userName: String,
        @Path("repoName") repoName: String
    ): Single<List<TagDTO>>

}
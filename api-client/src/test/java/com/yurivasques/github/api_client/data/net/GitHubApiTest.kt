package com.yurivasques.github.api_client.data.net

import android.content.Context
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GitHubApiTest {

    private val context = mock<Context>()
    private val retrofit = RetrofitFactory.getRetrofit(context, Gson(), OkHttpClientFactoryTest())

    private val service: GitHubApi
        get() = retrofit.create(GitHubApi::class.java)

    @Test
    fun getListReposWebservice() {
        service.getListRepos("jetbrains").test()
            .assertNoErrors()
    }

    @Test
    fun getListTagsWebservice() {
        service.getListTags("jetbrains", "kotlin").test()
            .assertNoErrors()
    }
}
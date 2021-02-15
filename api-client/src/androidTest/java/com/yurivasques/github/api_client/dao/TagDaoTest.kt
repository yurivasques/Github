package com.yurivasques.github.api_client.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.yurivasques.github.api_client.DatabaseFactoryTest
import com.yurivasques.github.api_client.data.persistence.AppDatabase
import com.yurivasques.github.api_client.data.persistence.entity.TagEntity
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class TagDaoTest {

    companion object {
        private const val USER_NAME = "userName"
        private const val REPO_NAME = "repoName"
        private const val REPO_ID = 1L
    }

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database =
            DatabaseFactoryTest.getDatabase(InstrumentationRegistry.getInstrumentation().context)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insert() {
        val id = "id1"
        val entity = TagEntity(id,"name", REPO_ID)

        assert(database.tagDao().insert(entity) > 0)

        Maybe.fromCallable { database.tagDao().get(id) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it.compareTo(entity) }
    }

    @Test
    fun delete() {
        val id = "id1"
        val entity = TagEntity(id, "name", REPO_ID)

        assert(database.tagDao().insert(entity) > 0)

        Maybe.fromCallable { database.tagDao().get(id) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it.compareTo(entity) }

        assert(database.tagDao().delete(entity) == 1)

        Maybe.fromCallable { database.tagDao().get(id) }.test()
            .assertNoErrors()
            .assertResult()
    }

    @Test
    fun get() {
        // Check table is empty
        val id = "id1"

        Maybe.fromCallable { database.tagDao().get(id) }.test()
            .assertResult()

        // Insert a repo & select it
        val entity = TagEntity(id, "name", REPO_ID)

        assert(database.tagDao().insert(entity) > 0)

        Maybe.fromCallable { database.tagDao().get(id) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it.compareTo(entity) }
    }

    @Test
    fun getAll() {
        // Check table is empty
        Single.fromCallable { database.tagDao().getAll(REPO_ID) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it.isEmpty() }

        // Insert two repos & select it
        val entity1 = TagEntity("id1", "name", REPO_ID)
        val entity2 = TagEntity("id2", "name", REPO_ID)

        assert(database.tagDao().insert(entity1) > 0)
        assert(database.tagDao().insert(entity2) > 0)

        Single.fromCallable { database.tagDao().getAll(REPO_ID) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue {
                it.size == 2
                        && it[0].compareTo(entity1)
                        && it[1].compareTo(entity2)
            }
    }

    private fun TagEntity?.compareTo(entity: TagEntity): Boolean =
        this?.run {
            id == entity.id
                    && name == entity.name
                    && repoId == entity.repoId

        } ?: false

}
package com.michaelfotiadis.demo.reddit.android.data.db

import androidx.paging.DataSource
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Insert(onConflict = REPLACE)
    suspend fun insert(post: Post)

    @Update(onConflict = REPLACE)
    suspend fun update(post: Post)

    @Update(onConflict = REPLACE)
    suspend fun updateMultiple(posts: List<Post>)

    @Delete
    suspend fun delete(post: Post)

    @Delete
    suspend fun deleteMultiple(posts: List<Post>)

    @Query("DELETE FROM $TABLE_NAME_POSTS")
    suspend fun nukeTable()

    @Query("SELECT * FROM $TABLE_NAME_POSTS")
    suspend fun getAll(): List<Post>

    @Query("SELECT * FROM $TABLE_NAME_POSTS ORDER BY createdUtc DESC")
    fun getAllPaged(): DataSource.Factory<Int, Post>

    @Transaction
    suspend fun deleteAndInsertEntries(posts: List<Post>) {
        val dbPosts = getAll()
        dbPosts.filter { dbPost ->
            posts.forEach { updatedPost ->
                if (updatedPost.id == dbPost.id) {
                    return@filter false
                }
            }
            return@filter true
        }.let { excludedDbPosts -> deleteMultiple(excludedDbPosts) }
        updateMultiple(posts)
    }

}
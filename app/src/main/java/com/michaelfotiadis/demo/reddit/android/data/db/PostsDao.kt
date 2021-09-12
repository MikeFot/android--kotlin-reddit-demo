package com.michaelfotiadis.demo.reddit.android.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(posts: List<Post>)

    @Insert(onConflict = REPLACE)
    suspend fun insert(post: Post)

    @Update(onConflict = REPLACE)
    suspend fun update(post: Post)

    @Query("DELETE FROM $TABLE_NAME_POSTS")
    suspend fun nukeTable()

    @Query("SELECT * FROM $TABLE_NAME_POSTS")
    suspend fun getAll(): List<Post>

    @Query("SELECT * FROM $TABLE_NAME_POSTS")
    fun getAllPaged(): DataSource.Factory<Int, Post>

}
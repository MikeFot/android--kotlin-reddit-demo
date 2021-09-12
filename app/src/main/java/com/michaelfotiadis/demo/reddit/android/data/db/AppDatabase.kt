package com.michaelfotiadis.demo.reddit.android.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postsDao(): PostsDao

}
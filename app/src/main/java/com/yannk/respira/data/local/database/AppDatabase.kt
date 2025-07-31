package com.yannk.respira.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yannk.respira.data.local.dao.UserDao
import com.yannk.respira.data.local.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
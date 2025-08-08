package com.yannk.respira.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yannk.respira.data.local.dao.UserDao
import com.yannk.respira.data.local.model.SessionEntity
import com.yannk.respira.data.local.model.User

@Database(entities = [User::class, SessionEntity::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}
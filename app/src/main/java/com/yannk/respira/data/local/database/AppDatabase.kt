package com.yannk.respira.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yannk.respira.data.local.dao.ReportsDao
import com.yannk.respira.data.local.dao.SessionDao
import com.yannk.respira.data.local.dao.UserDao
import com.yannk.respira.data.local.model.DailyReportEntity
import com.yannk.respira.data.local.model.SessionEntity
import com.yannk.respira.data.local.model.User

@Database(entities = [User::class, SessionEntity::class, DailyReportEntity::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun sessionDao(): SessionDao
    abstract fun reportsDao(): ReportsDao
}
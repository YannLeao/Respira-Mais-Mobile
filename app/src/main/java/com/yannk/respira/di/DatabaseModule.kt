package com.yannk.respira.di

import android.content.Context
import androidx.room.Room
import com.yannk.respira.data.local.dao.SessionDao
import com.yannk.respira.data.local.dao.UserDao
import com.yannk.respira.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_db"
        ).build()
    }
    
    @Provides
    fun providesUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    fun providesSessionDao(db: AppDatabase): SessionDao {
        return db.sessionDao()
    }
}
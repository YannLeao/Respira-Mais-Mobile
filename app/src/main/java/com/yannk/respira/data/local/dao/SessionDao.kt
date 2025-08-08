package com.yannk.respira.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yannk.respira.data.local.model.SessionEntity

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionEntity)

    @Query("SELECT * FROM session_table ORDER BY dataHoraFim DESC LIMIT 1")
    suspend fun getLatestSession(): SessionEntity?

    @Query("DELETE FROM session_table")
    suspend fun clearAll()
}

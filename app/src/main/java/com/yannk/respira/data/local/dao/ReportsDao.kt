package com.yannk.respira.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yannk.respira.data.local.model.DailyReportEntity

@Dao
interface ReportsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyReport(report: DailyReportEntity)

    @Query("SELECT * FROM daily_reports WHERE date = :date")
    suspend fun getDailyReport(date: String): DailyReportEntity?

    @Query("DELETE FROM daily_reports WHERE date = :date")
    suspend fun deleteDailyReport(date: String)

    @Query(
        "SELECT * FROM daily_reports WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC"
    )
    suspend fun getReportsInRange(startDate: String, endDate: String): List<DailyReportEntity>

    @Query("DELETE FROM daily_reports")
    suspend fun clear()
}
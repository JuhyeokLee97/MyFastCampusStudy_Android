package com.example.myfastcampusstudy_android.basic.calculator

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myfastcampusstudy_android.basic.calculator.dao.HistoryDao
import com.example.myfastcampusstudy_android.basic.calculator.model.History


@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}
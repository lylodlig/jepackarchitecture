package com.example.jepack_architecture.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.jepack_architecture.MyApp

/**
 * 数据库文件
 */
@Database(entities = [HomeArticleDetail::class], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    // 得到UserDao
    abstract fun homeDao(): HomeArticleCacheDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getInstance(): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase()
                    .also {
                        instance = it
                    }
            }
        }

        private fun buildDataBase(): AppDataBase {
            return Room
                .databaseBuilder(MyApp.instance, AppDataBase::class.java, "jetPackDemo-database.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // 读取鞋的集合
                        /*val request = OneTimeWorkRequestBuilder<ShoeWorker>().build()
                        WorkManager.getInstance().enqueue(request)*/
                    }
                })
                .build()
        }
    }
}
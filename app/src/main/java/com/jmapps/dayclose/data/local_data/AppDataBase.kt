package com.jmapps.dayclose.data.local_data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jmapps.dayclose.model.DayDataEntity
import com.jmapps.dayclose.model.DayExpenseEntity


@Database(entities = [DayDataEntity::class, DayExpenseEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun dayDataDao(): DayDataDao
    abstract fun dayExpenseDao(): DayExpenseDao



    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "day_close_data"
                ).fallbackToDestructiveMigration(false).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
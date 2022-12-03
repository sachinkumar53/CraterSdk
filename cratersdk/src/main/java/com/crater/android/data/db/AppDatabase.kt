package com.crater.android.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crater.android.feature.invoicing.data.dao.CustomerDao
import com.crater.android.feature.invoicing.data.dao.ServiceDao
import com.crater.android.feature.invoicing.data.entity.CustomerEntity
import com.crater.android.feature.invoicing.data.entity.ServiceEntity
import com.crater.android.feature.expense.data.converter.LocalDateConverter
import com.crater.android.feature.expense.data.converter.YearMonthConverter
import com.crater.android.feature.expense.data.dao.TransactionDao
import com.crater.android.feature.expense.data.entity.TransactionDetailEntity

@TypeConverters(
    value = [
        LocalDateConverter::class,
        YearMonthConverter::class
    ]
)
@Database(
    entities = [ServiceEntity::class, CustomerEntity::class, TransactionDetailEntity::class],
    version = 1,
//    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val serviceDao: ServiceDao
    abstract val customerDao: CustomerDao
    abstract val transactionDao: TransactionDao

    companion object {
        @Volatile
        private var sInstance: AppDatabase? = null
        private const val DB_NAME = "crater_db"

        operator fun invoke(context: Context): AppDatabase {
            return sInstance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).build().also {
                    sInstance = it
                }
            }
        }
    }
}
package com.crater.android.feature.expense.data.di

import android.content.Context
import com.crater.android.data.db.AppDatabase
import com.crater.android.feature.expense.data.dao.TransactionDao
import com.crater.android.feature.expense.data.sms.SmsFetcher
import com.crater.android.feature.expense.data.sms.filter.SmsRecognizer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideTransactionDao(db: AppDatabase): TransactionDao = db.transactionDao

    @Singleton
    @Provides
    fun provideSmsRecognizer(): SmsRecognizer = SmsRecognizer()

    @Singleton
    @Provides
    fun provideSmsFetcher(
        @ApplicationContext
        context: Context,
        smsRecognizer: SmsRecognizer
    ): SmsFetcher {
        return SmsFetcher(context, smsRecognizer)
    }
}
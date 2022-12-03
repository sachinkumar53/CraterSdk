package com.crater.android.feature.expense.data.di

import com.crater.android.feature.expense.data.dao.TransactionDao
import com.crater.android.feature.expense.data.mapper.TransactionMapper
import com.crater.android.feature.expense.data.repository.ExpenseRepositoryImpl
import com.crater.android.feature.expense.data.sms.SmsFetcher
import com.crater.android.feature.expense.domain.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideExpenseRepository(
        smsFetcher: SmsFetcher,
        transactionDao: TransactionDao
    ): ExpenseRepository {
        return ExpenseRepositoryImpl(smsFetcher, transactionDao, TransactionMapper())
    }
}
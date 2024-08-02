package com.addepar.investfolio.di

import android.app.Application
import android.content.Context
import com.addepar.investfolio.repository.InvestmentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideInvestmentRepository(context: Context): InvestmentRepository {
        return InvestmentRepository(context)
    }
}

package com.janpschwietzer.calpal.di

import android.app.Application
import com.janpschwietzer.calpal.data.repository.UserRepository
import com.janpschwietzer.calpal.data.repository.UserRepositoryImpl
import com.janpschwietzer.calpal.data.source.local.AppDatabase
import com.janpschwietzer.calpal.data.source.local.UserDao
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
    fun provideAppDatabase(application: Application): AppDatabase {
        return AppDatabase.getDatabase(application)
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }
}

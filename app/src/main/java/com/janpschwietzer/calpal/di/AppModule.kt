package com.janpschwietzer.calpal.di

import android.app.Application
import com.janpschwietzer.calpal.data.repository.EatenProductRepository
import com.janpschwietzer.calpal.data.repository.EatenProductRepositoryImpl
import com.janpschwietzer.calpal.data.repository.ProductRepository
import com.janpschwietzer.calpal.data.repository.ProductRepositoryImpl
import com.janpschwietzer.calpal.data.repository.UserRepository
import com.janpschwietzer.calpal.data.repository.UserRepositoryImpl
import com.janpschwietzer.calpal.data.source.local.AppDatabase
import com.janpschwietzer.calpal.data.source.local.EatenProductDao
import com.janpschwietzer.calpal.data.source.local.ProductDao
import com.janpschwietzer.calpal.data.source.local.UserDao
import com.janpschwietzer.calpal.data.source.remote.ProductApiService
import com.janpschwietzer.calpal.data.source.remote.RetrofitClient
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
    fun provideProductDao(appDatabase: AppDatabase): ProductDao {
        return appDatabase.productDao()
    }

    @Provides
    @Singleton
    fun provideEatenProductDao(appDatabase: AppDatabase): EatenProductDao {
        return appDatabase.eatenProductDao()
    }

    @Provides
    @Singleton
    fun provideProductApiService(): ProductApiService {
        return RetrofitClient.apiService
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productDao: ProductDao,
        productApiService: ProductApiService
    ): ProductRepository {
        return ProductRepositoryImpl(productDao, productApiService)
    }

    @Provides
    @Singleton
    fun provideEatenProductRepository(eatenProductDao: EatenProductDao): EatenProductRepository {
        return EatenProductRepositoryImpl(eatenProductDao)
    }
}

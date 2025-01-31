package com.janpschwietzer.calpal.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.janpschwietzer.calpal.data.repository.ProductRepository
import com.janpschwietzer.calpal.data.repository.ProductRepositoryImpl
import com.janpschwietzer.calpal.data.repository.UserRepository
import com.janpschwietzer.calpal.data.repository.UserRepositoryImpl
import com.janpschwietzer.calpal.data.source.local.AppDatabase
import com.janpschwietzer.calpal.data.source.local.ProductDao
import com.janpschwietzer.calpal.data.source.local.ProductDatabase
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
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    // Product Database und API Service bereitstellen
    @Provides
    @Singleton
    fun provideProductDatabase(context: Context): ProductDatabase {
        return Room.databaseBuilder(
            context,
            ProductDatabase::class.java,
            "product_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideProductApiService(): ProductApiService {
        return RetrofitClient.apiService
    }

    @Provides
    fun provideProductDao(productDatabase: ProductDatabase): ProductDao {
        return productDatabase.productDao()
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productDao: ProductDao,
        productApiService: ProductApiService
    ): ProductRepository {
        return ProductRepositoryImpl(productDao, productApiService)
    }
}

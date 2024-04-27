package com.example.examples.retrofit.di

import com.example.examples.retrofit.CommentsApi
import com.example.examples.retrofit.CommentsRepository
import com.example.examples.retrofit.CommentsRepositoryImpl
import com.example.examples.retrofit.RetrofitHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommentsModule {

    @Provides
    @Singleton
    fun provideCommentsApi(): CommentsApi {
        return RetrofitHelper.getInstance().create(CommentsApi::class.java)
    }

}
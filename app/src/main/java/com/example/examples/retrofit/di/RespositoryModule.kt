package com.example.examples.retrofit.di

import com.example.examples.retrofit.CommentsRepository
import com.example.examples.retrofit.CommentsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RespositoryModule {

    @Binds
    @Singleton
    abstract fun bindCommentsRepository(
        commentsRepositoryImpl: CommentsRepositoryImpl
    ): CommentsRepository

}
package com.shalva97.jellylist.stackvoerflow

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Provides
    @Singleton
    fun authRepo(@ApplicationContext context: Context): AuthenticationRepo {
        return AuthenticationRepo(context)
    }

}
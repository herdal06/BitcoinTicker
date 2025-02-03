package com.herdal.bitcointicker.features.user.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.herdal.bitcointicker.features.user.data.firebase.datasource.UserFirebaseDataSource
import com.herdal.bitcointicker.features.user.data.firebase.datasource.UserFirebaseDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object CoinModule {
    @Provides
    @Singleton
    fun provideUserFirebaseDataSource(
        firestore: FirebaseFirestore
    ): UserFirebaseDataSource {
        return UserFirebaseDataSourceImpl(firestore)
    }
}
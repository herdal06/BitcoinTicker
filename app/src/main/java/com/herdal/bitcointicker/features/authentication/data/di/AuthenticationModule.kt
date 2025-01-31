package com.herdal.bitcointicker.features.authentication.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.herdal.bitcointicker.core.di.IoDispatcher
import com.herdal.bitcointicker.features.authentication.data.firebase.datasource.AuthenticationDataSource
import com.herdal.bitcointicker.features.authentication.data.firebase.datasource.AuthenticationDataSourceImpl
import com.herdal.bitcointicker.features.authentication.data.repository.AuthenticationRepositoryImpl
import com.herdal.bitcointicker.features.authentication.domain.AuthenticationRepository
import com.herdal.bitcointicker.features.user.data.firebase.datasource.UserFirebaseDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object AuthenticationModule {
    @Provides
    @Singleton
    fun provideAuthInstance(): FirebaseAuth {
        return Firebase.auth
    }

    @Provides
    @Singleton
    fun provideFirestoreInstance(): FirebaseFirestore {
        return Firebase.firestore
    }

    @Provides
    @Singleton
    fun provideAuthenticationDataSource(
        firebaseAuth: FirebaseAuth
    ): AuthenticationDataSource {
        return AuthenticationDataSourceImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        authenticationDataSource: AuthenticationDataSource,
        userFirebaseDataSource: UserFirebaseDataSource,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(
            authenticationDataSource,
            userFirebaseDataSource,
            ioDispatcher
        )
    }
}
package com.herdal.bitcointicker.features.coin.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.herdal.bitcointicker.core.data.local.CoinDatabase
import com.herdal.bitcointicker.features.authentication.data.firebase.datasource.AuthenticationDataSource
import com.herdal.bitcointicker.features.coin.data.firebase.datasource.CoinFirebaseDataSource
import com.herdal.bitcointicker.features.coin.data.firebase.datasource.CoinFirebaseDataSourceImpl
import com.herdal.bitcointicker.features.coin.data.local.dao.CoinDao
import com.herdal.bitcointicker.features.coin.data.local.datasource.CoinLocalDataSource
import com.herdal.bitcointicker.features.coin.data.local.datasource.CoinLocalDataSourceImpl
import com.herdal.bitcointicker.features.coin.data.remote.datasource.CoinRemoteDataSource
import com.herdal.bitcointicker.features.coin.data.remote.datasource.CoinRemoteDataSourceImpl
import com.herdal.bitcointicker.features.coin.data.remote.service.CoinService
import com.herdal.bitcointicker.features.coin.data.repository.CoinRepositoryImpl
import com.herdal.bitcointicker.features.coin.domain.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
object CoinModule {
    @Provides
    @Singleton
    fun provideCoinService(retrofit: Retrofit): CoinService {
        return retrofit.create(CoinService::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinDao(database: CoinDatabase): CoinDao {
        return database.coinDao()
    }

    @Provides
    @Singleton
    fun provideCoinRemoteDataSource(
        coinService: CoinService
    ): CoinRemoteDataSource {
        return CoinRemoteDataSourceImpl(coinService)
    }

    @Provides
    @Singleton
    fun provideCoinLocalDataSource(
        coinDao: CoinDao
    ): CoinLocalDataSource {
        return CoinLocalDataSourceImpl(coinDao)
    }

    @Provides
    @Singleton
    fun provideCoinFirebaseDataSource(
        firestore: FirebaseFirestore
    ): CoinFirebaseDataSource {
        return CoinFirebaseDataSourceImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(
        remoteDataSource: CoinRemoteDataSource,
        localDataSource: CoinLocalDataSource,
        authenticationDataSource: AuthenticationDataSource,
        coinFirebaseDataSource: CoinFirebaseDataSource
    ): CoinRepository {
        return CoinRepositoryImpl(
            remoteDataSource,
            localDataSource,
            authenticationDataSource,
            coinFirebaseDataSource
        )
    }
}
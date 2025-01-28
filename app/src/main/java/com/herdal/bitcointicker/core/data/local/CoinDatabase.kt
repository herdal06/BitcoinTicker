package com.herdal.bitcointicker.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.herdal.bitcointicker.features.coin.data.local.dao.CoinDao
import com.herdal.bitcointicker.features.coin.data.local.entity.CoinEntity

@Database(
    entities = [
        CoinEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}
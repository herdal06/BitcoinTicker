package com.herdal.bitcointicker.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.herdal.bitcointicker.features.coin.data.local.converter.RoinEntityConverter
import com.herdal.bitcointicker.features.coin.data.local.dao.CoinDao
import com.herdal.bitcointicker.features.coin.data.local.entity.CoinEntity
import com.herdal.bitcointicker.features.coin.data.local.entity.RoinEntity

@Database(
    entities = [
        CoinEntity::class,
        RoinEntity::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(RoinEntityConverter::class)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}
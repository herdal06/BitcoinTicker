package com.herdal.bitcointicker.features.coin.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.herdal.bitcointicker.features.coin.data.local.entity.RoinEntity

class RoinEntityConverter {
    @TypeConverter
    fun fromRoinEntity(roi: RoinEntity?): String? = roi?.let { Gson().toJson(it) }

    @TypeConverter
    fun toRoinEntity(roiString: String?): RoinEntity? =
        roiString?.let {
            Gson().fromJson(it, object : TypeToken<RoinEntity>() {}.type)
        }
}
package com.herdal.bitcointicker.features.user.data.firebase.datasource

import com.herdal.bitcointicker.core.data.remote.IResult
import com.herdal.bitcointicker.features.user.data.firebase.model.UserFirebaseModel

interface UserFirebaseDataSource {
    suspend fun saveUser(user: UserFirebaseModel): IResult<Unit>
    suspend fun getUser(userId: String): IResult<UserFirebaseModel>
}
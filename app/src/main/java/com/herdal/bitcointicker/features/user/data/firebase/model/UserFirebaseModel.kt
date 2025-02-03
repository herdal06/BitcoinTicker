package com.herdal.bitcointicker.features.user.data.firebase.model

import com.herdal.bitcointicker.features.user.domain.uimodel.UserUiModel

data class UserFirebaseModel(
    val id: String?,
    val email: String?,
    val createdAt: Long = System.currentTimeMillis()
)

fun UserFirebaseModel.toDomain(): UserUiModel = UserUiModel(
    id = id,
    email = email,
    createdAt = createdAt
)
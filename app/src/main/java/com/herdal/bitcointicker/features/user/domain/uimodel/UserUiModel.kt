package com.herdal.bitcointicker.features.user.domain.uimodel

data class UserUiModel(
    val id: String?,
    val email: String?,
    val createdAt: Long = System.currentTimeMillis()
)
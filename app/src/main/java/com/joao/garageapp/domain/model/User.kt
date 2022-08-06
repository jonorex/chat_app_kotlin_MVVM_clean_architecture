package com.joao.garageapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User (
    var userId: String = "",
    val name: String = "",
    val image: String = "",
    val email: String = "",
    val token: String = "",
    ) : Parcelable
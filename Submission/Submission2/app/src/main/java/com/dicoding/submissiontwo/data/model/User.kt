package com.dicoding.submissiontwo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login: String,
    val avatar_url: String
) : Parcelable

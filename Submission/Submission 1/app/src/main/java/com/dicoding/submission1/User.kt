package com.dicoding.submission1

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class User(
    var imgUser: Int?,
    var name: String?,
    var username: String?,
    var company: String?,
    var location: String?,
    var repository: String?,
    var followers: String?,
    var following: String?,
) : Parcelable
package com.example.allmen

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    var id: String?,
    var userName: String?,
    var email: String,
    var password: String
    ) : Parcelable
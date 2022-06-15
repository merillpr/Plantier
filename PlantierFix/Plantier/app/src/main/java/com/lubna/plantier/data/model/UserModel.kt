package com.lubna.plantier.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserModel(
    var username: String,
    var password: String,
    var isLogin: Boolean
)

data class DataAnalysis (
    val name: String,
    val detail: String,
    val treatment: String
)

@Parcelize
data class Disease(
    val disease_name: String,
    val disease_photo: Int,
    val disease_desc: String,
    val disease_treat: String
) : Parcelable

@Parcelize
data class Benefit(
    val benefit: String,
    val benefit_photo: Int,
    val benefit_desc: String
) : Parcelable

@Parcelize
data class DetailModel (
    val name: String,
    val description: String,
    val solution: String
):Parcelable
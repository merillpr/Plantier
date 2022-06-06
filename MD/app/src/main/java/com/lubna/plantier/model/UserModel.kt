package com.lubna.plantier.model

data class UserModel(
    val name: String,
    val email: String,
    val password: String,
    val isLogin: Boolean
)

data class DataAnalysis (
    val name: String,
    val detail: String,
    val treatment: String
)

data class Disease(
    val diseaseImage: Int,
    val diseaseName: String,
    val diseaseDesc: String,
    val preventDesc: String
)

data class Benefit(
    val benefitImage: Int,
    val benefitName: String
)
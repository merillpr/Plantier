package com.lubna.plantier.data.response

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

class AnalysisResponse(
    @field:SerializedName("Model")
    var Model: ModelItem,

    @field:SerializedName("message")
    var message: String
)

class ModelItem(
    @field:SerializedName("id")
    var id: String,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("description")
    var description: String,

    @field:SerializedName("solution")
    var solution: String
)
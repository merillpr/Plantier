package com.lubna.plantier.ui.analysis

import com.google.gson.annotations.SerializedName

class AnalysisResponse(
    @field:SerializedName("model")
    var model: ModelItem,

    @field:SerializedName("message")
    var message: String
)

class ModelItem(
    @field:SerializedName("id")
    var id: Int,

    @field:SerializedName("name")
    var name: String,

    @field:SerializedName("description")
    var description: String,

    @field:SerializedName("solution")
    var solution: String
)

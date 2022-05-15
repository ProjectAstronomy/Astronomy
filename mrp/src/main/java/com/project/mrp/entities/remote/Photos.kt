package com.project.mrp.entities.remote

import com.google.gson.annotations.SerializedName

data class Photos(@field:SerializedName("photos") val photos: List<Photo>?)

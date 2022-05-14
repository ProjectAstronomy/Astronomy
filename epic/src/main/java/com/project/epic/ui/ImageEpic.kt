package com.project.epic.ui

object ImageEpic {

    fun urlEpicImage(dateImage: String, nameImage: String): String {
        val imageEpicBegin = "https://epic.gsfc.nasa.gov/archive/natural/"
        return "$imageEpicBegin${dateImageEpic(dateImage)}/jpg/$nameImage.jpg"
    }

    fun dateImageEpic(date: String): String {
        return date.substringBefore(" ").replace("-", "/")
    }
}
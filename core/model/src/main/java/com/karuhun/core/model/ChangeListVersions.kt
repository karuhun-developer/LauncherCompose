package com.karuhun.core.model

data class ChangeListVersions(
    val foodVersion: Int = -1,
    val foodCategoryVersion: Int = -1,
    val applicationVersion: Int = -1,
    val contentsVersion: Int = -1,
    val contentItemsVersion: Int = -1,
)
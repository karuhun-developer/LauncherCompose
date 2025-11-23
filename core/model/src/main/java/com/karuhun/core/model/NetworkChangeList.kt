package com.karuhun.core.model

import com.google.gson.annotations.SerializedName

data class NetworkChangeList(

    @field:SerializedName("deleted_at")
	val deletedAt: String?,

    @field:SerializedName("id")
	val id: Int,

    @field:SerializedName("version")
	val version: Int
)
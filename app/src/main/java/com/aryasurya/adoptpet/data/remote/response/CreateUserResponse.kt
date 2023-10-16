package com.aryasurya.adoptpet.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreateUserResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

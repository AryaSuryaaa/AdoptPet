package com.aryasurya.adoptpet.data.remote.retrofit

import com.aryasurya.adoptpet.data.remote.response.CreateUserResponse
import com.aryasurya.adoptpet.data.remote.response.DetailStoriesResponse
import com.aryasurya.adoptpet.data.remote.response.FileUploadResponse
import com.aryasurya.adoptpet.data.remote.response.ListStoryItem
import com.aryasurya.adoptpet.data.remote.response.LoginResponse
import com.aryasurya.adoptpet.data.remote.response.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    suspend fun createUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") string: String
    ): CreateUserResponse


    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") string: String
    ): LoginResponse

    @GET("stories")
    suspend fun getStories(): StoriesResponse

    @Multipart
    @POST("stories")
    suspend fun postStory(
        @Part file: MultipartBody.Part ,
        @Part("description") description: RequestBody ,
    ): FileUploadResponse

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id: String
    ): DetailStoriesResponse

}
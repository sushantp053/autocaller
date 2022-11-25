package com.deecto.autocaller.connection

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


/**
 * Created by adinugroho
 */
internal interface Service {
    @Multipart
    @POST("/")
    fun postImage(@Part image: Part?, @Part("name") name: RequestBody?): Call<ResponseBody?>?
}
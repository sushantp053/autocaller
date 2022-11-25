package com.deecto.autocaller.connection

import com.deecto.autocaller.data.CampStatus
import com.deecto.autocaller.data.Status
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

const val BaseUrl = "http://192.168.65.139:3000"

interface StatusInterface {

    @GET("/getKitCurrentStatus")
    fun getStatus(): retrofit2.Call<Status>


    @GET("/getCurrCampStatus")
    fun getCampaignStatus(): retrofit2.Call<CampStatus>


    @Multipart
    @POST("/downloadcontactlist")
    suspend fun uploadFile(@Part filePart: MultipartBody.Part): ResponseBody

}

object StatusService{
    val statusInstance: StatusInterface
    init {
        val retrofit = Retrofit.Builder()
        .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        statusInstance = retrofit.create(StatusInterface::class.java)

    }
}
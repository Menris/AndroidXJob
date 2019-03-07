package kz.cheesenology.androidxtest.network

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface NetworkApi {

    @FormUrlEncoded
    @POST("/mobile/login")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    fun testRequest(@Field("login") login: String, @Field("password") password: String): Call<ResponseBody>
}
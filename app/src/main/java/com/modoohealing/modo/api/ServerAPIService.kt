package com.modoohealing.modo.api

import com.modoohealing.modo.datamodel.BasicResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ServerAPIService {
    //로그인 하는 기능 명세
    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email:String,
        @Field("password") pw:String,
    ) : Call<BasicResponse>
}
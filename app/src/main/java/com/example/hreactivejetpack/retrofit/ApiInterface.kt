package com.example.hreactivejetpack.retrofit

import com.example.hreactivejetpack.model.AllEmployeeResponse
import com.example.hreactivejetpack.model.EmployeeDetailResposne
import com.example.hreactivejetpack.model.LoginModel
import com.example.hreactivejetpack.model.LoginResponse
import com.example.hreactivejetpack.model.Response.EditProfileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiInterface {
    @GET("hr/allEmployee/{orgId}")
    suspend fun getAllEmployee(
        @Header("Authorization") authorization: String,
        @Path("orgId") orgID: String
    ): AllEmployeeResponse

    @POST("employee/signIn")
    suspend fun userLogin(@Body loginModel: LoginModel): Response<LoginResponse>

    @GET("employee/details/{employeeId}")
    suspend fun getHomeDetail(
        @Header("Authorization") authorization:String,
        @Path("employeeId") employeeId:Int
    ):EmployeeDetailResposne

    @FormUrlEncoded
    @POST("employee/editprofile")
    suspend fun editProfileWithoutImage(
        @Header("Authorization") Authorization: String,
        @Field("userId") userId: Int,
        @Field("firstName") firstName: String,
        @Field("lastName") lastName: String,
        @Field("dob") dob: String,
        @Field("gender") gender: String,
        @Field("maritalStatus") maritalStatus: String,
        @Field("nationality") nationality: String,
        @Field("userName") userName: String,
        @Field("email") email: String,
        @Field("countryCode") countryCode: Int,
        @Field("mobileNumber") mobileNumber: String,
        @Field("currentAddress") currentAddress: String,
        @Field("degreeEducation") degreeEducation: String,
        @Field("intermediate") intermediate: String,
        @Field("highSchool") highSchool: String
    ): EditProfileResponse


    @Multipart
    @POST("employee/editprofile")
    suspend fun editProfileWithImage(
        @Header("Authorization") Authorization: String,
        @Part image: MultipartBody.Part,
        @Part("userId") userId: Int,
        @Part("firstName") firstName: RequestBody,
        @Part("lastName") lastName: RequestBody,
        @Part("dob") dob: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("maritalStatus") maritalStatus: RequestBody,
        @Part("nationality") nationality: RequestBody,
        @Part("userName") userName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("countryCode") countryCode: Int,
        @Part("mobileNumber") mobileNumber: RequestBody,
        @Part("currentAddress") currentAddress: RequestBody,
        @Part("degreeEducation") degreeEducation: RequestBody,
        @Part("intermediate") intermediate: RequestBody,
        @Part("highSchool") highSchool: RequestBody
    ): EditProfileResponse
}
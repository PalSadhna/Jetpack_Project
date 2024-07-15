package com.example.hreactivejetpack.retrofit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitInstance {
    val baseUrl = "https://hreactive.teckhubsolutions.com/"

    @Singleton
    @Provides
    fun getRetroServiceInterface(retrofit: Retrofit): ApiInterface{
        return retrofit.create(ApiInterface::class.java)
    }

    @Singleton
    @Provides
    fun getRetrofitInstance():Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
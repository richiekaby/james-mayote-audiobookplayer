package net.larntech.retrofit_users.client

import net.larntech.retrofit_users.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {


    fun getRetrofit(): Retrofit{


        val logger = HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)


        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build();


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.larntech.net/")
            .client(client)
            .build()

        return retrofit;

    }


    fun getApiService(): ApiService{
        return getRetrofit().create(ApiService::class.java)
    }


}
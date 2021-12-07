package net.larntech.retrofit12.network.apiclient

import net.larntech.retrofit12.network.service.ApiServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    fun getRetrofit(): Retrofit{

        val logger = HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build();

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.larntech.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();


        return retrofit;

    }


    fun getApiService(): ApiServices{
        return getRetrofit().create(ApiServices::class.java)
    }


}
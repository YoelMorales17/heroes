package com.yoel.examencoppel2.core

import com.yoel.examencoppel2.utilities.Utils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CreateConnection {

    fun createConnection(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10,TimeUnit.SECONDS).build()
        val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(Utils().url)
            .addConverterFactory(GsonConverterFactory.create()).client(client)
        return builder.build()

    }
}
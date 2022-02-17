package com.yoel.examencoppel2.core

import com.google.gson.JsonObject
import com.yoel.examencoppel2.model.HeroObject
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HeroClient {
    @GET("characters")
    suspend fun getHeroes(
        @Query("apikey") apikey: String,
        @Query("ts") timestamp: String,
        @Query("hash") hash: String,
        @Query("limit") limit: String,
        @Query("offset") offset: Int
    ): Response<JsonObject>
}
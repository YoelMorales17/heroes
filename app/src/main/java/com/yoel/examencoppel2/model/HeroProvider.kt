package com.yoel.examencoppel2.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.yoel.examencoppel2.core.CreateConnection
import com.yoel.examencoppel2.core.HeroClient
import com.yoel.examencoppel2.utilities.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HeroProvider {
    val progress = MutableLiveData<Boolean>()
    var heroes = MutableLiveData<ArrayList<HeroObject>>()
    var heroesSearch = MutableLiveData<ArrayList<HeroObject>>()

    var code = 99
    fun changeState() {
        progress.value = !(progress.value != null && progress.value!!)
    }

    suspend fun getHeroes(offset : Int) {
        var jsonArray = JsonArray()
        val call = CreateConnection().createConnection().create(HeroClient::class.java)
            .getHeroes(Utils().apiKey, Utils().timestamp, Utils().hashKey, Utils().limit,offset)
        if (call.isSuccessful) {
            if (call.body() != null) {
                code = call.body()!!["code"].asString.toInt()
                if (code == 200) {
                    val jsonData = call.body()!!["data"].asJsonObject
                    jsonArray = jsonData["results"].asJsonArray
                }
            }
        } else {
            Log.d("RESSSSSSS", call.message())
        }
        withContext(Dispatchers.Main) {
            changeState()
            heroes.value = parseData(jsonArray)
        }
    }

    fun getHeroesSearch(searchText: String, array: ArrayList<HeroObject>) {
        val arraySearch = ArrayList<HeroObject>()
        for (i in 0 until array.size) {
            if (array[i].name.lowercase().contains(searchText.lowercase().toRegex())) {
                arraySearch.add(array[i])
            }
        }
        heroesSearch.value = arraySearch

    }

    private fun parseData(jsonArray: JsonArray): ArrayList<HeroObject> {
        val arrayHeroes = ArrayList<HeroObject>()
        var heroObject: HeroObject
        for (i in 0 until jsonArray.size()) {
            heroObject = HeroObject()
            val jsoObject = jsonArray[i].asJsonObject
            heroObject.id = jsoObject["id"].asString
            heroObject.name = jsoObject["name"].asString
            heroObject.description = jsoObject["description"].asString
            val thumbnailObject = jsoObject["thumbnail"].asJsonObject
            heroObject.thumbnail.path = thumbnailObject["path"].asString
            heroObject.thumbnail.extension = thumbnailObject["extension"].asString
            heroObject.comics = jsoObject["comics"].asJsonObject["available"].asString.toInt()
            heroObject.comics = jsoObject["series"].asJsonObject["available"].asString.toInt()

            arrayHeroes.add(heroObject)
        }
        return arrayHeroes
    }
}
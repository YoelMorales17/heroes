package com.yoel.examencoppel2.viewModel

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoel.examencoppel2.R
import com.yoel.examencoppel2.model.HeroObject
import com.yoel.examencoppel2.model.HeroProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class HeroViewModel : ViewModel() {
    val repository = HeroProvider()
    val progress : LiveData<Boolean>

    init {
        this.progress = repository.progress
    }


    fun getHeroes(offset : Int){
        repository.changeState()
        viewModelScope.launch {
            withTimeoutOrNull(10000) {
                repository.getHeroes(offset)
            }
        }
    }
    fun getHeroesSearch(searchText : String,array : ArrayList<HeroObject>){
        repository.getHeroesSearch(searchText,array)
    }

    fun changueBackGround(context : Context,show : View,hide1 : View,hide2 : View){
        show.background = ContextCompat.getDrawable(context, R.drawable.search_back)
        hide1.background = null
        hide2.background = null
    }
}
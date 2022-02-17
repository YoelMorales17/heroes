package com.yoel.examencoppel2.utilities

import android.app.Application
import androidx.navigation.NavController
import com.yoel.examencoppel2.model.HeroObject

class GlobalClass : Application() {
    companion object{
        var heroSelected = HeroObject()
        var offset = 0
    }
}
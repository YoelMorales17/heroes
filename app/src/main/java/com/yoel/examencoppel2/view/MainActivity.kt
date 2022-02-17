package com.yoel.examencoppel2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.yoel.examencoppel2.R
import com.yoel.examencoppel2.model.HeroProvider
import com.yoel.examencoppel2.utilities.GlobalClass
import com.yoel.examencoppel2.viewModel.HeroViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}
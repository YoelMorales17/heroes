package com.yoel.examencoppel2.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.yoel.examencoppel2.R
import com.yoel.examencoppel2.databinding.FragmentDetailHeroBinding
import com.yoel.examencoppel2.model.HeroObject
import com.yoel.examencoppel2.utilities.GlobalClass


class DetailHeroFragment : Fragment() {
    lateinit var binding: FragmentDetailHeroBinding
    lateinit var hero : HeroObject
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDetailHeroBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hero = GlobalClass.heroSelected
        Log.d("HERO",hero.toString())
        ViewCompat.setTransitionName(binding.photoHero, "hero_image")
        Picasso.with(requireContext())
            .load("${hero.thumbnail.path}.${hero.thumbnail.extension}").into(binding.photoHero)
        when{
            hero.description != "" -> binding.description.text = hero.description
            else -> binding.description.text = getString(R.string.no_description)
        }
        binding.nameHero.text = hero.name

        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
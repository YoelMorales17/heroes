package com.yoel.examencoppel2.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.*
import com.yoel.examencoppel2.R
import com.yoel.examencoppel2.databinding.FragmentHeoresBinding
import com.yoel.examencoppel2.model.HeroObject
import com.yoel.examencoppel2.utilities.GlobalClass
import com.yoel.examencoppel2.viewModel.HeroViewModel
import kotlinx.coroutines.*


class HeoresFragment : Fragment() {
    lateinit var binding: FragmentHeoresBinding
    lateinit var viewModel: HeroViewModel
    lateinit var dialog: AlertDialog
    var arrayHeroes = ArrayList<HeroObject>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHeoresBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        viewModel.getHeroes(GlobalClass.offset)
        when(GlobalClass.offset){
            0 -> {
                viewModel.changueBackGround(requireContext(),binding.firstPage,binding.secondPage,binding.thirdPage)
            }
            100 -> {
                viewModel.changueBackGround(requireContext(),binding.secondPage,binding.firstPage,binding.thirdPage)

            }
            200 -> {
                viewModel.changueBackGround(requireContext(),binding.thirdPage,binding.secondPage,binding.firstPage)
            }

        }
        onClicks()

    }
    private fun setObservers() {
        viewModel = ViewModelProvider(this)[HeroViewModel::class.java]

        viewModel.progress.observe(this, Observer {
            if (it) binding.loading.visibility = VISIBLE
            else binding.loading.visibility = GONE
        })
        viewModel.repository.heroes.observe(this, Observer {
            arrayHeroes = it
            CoroutineScope(Dispatchers.Main).launch {
                checkResult(it)
            }
        })
        viewModel.repository.heroesSearch.observe(this, Observer {
            CoroutineScope(Dispatchers.Main).launch {
                checkResult(it)
            }
        })
    }

    private suspend fun checkResult(result: ArrayList<HeroObject>?) {
        withContext(Dispatchers.Main) {
            if (result != null) {
                val layoutManager = LinearLayoutManager(requireContext())
                val adapter = HeroesAdapter(requireContext(), result) { hero ->
                    GlobalClass.heroSelected = hero
                    this@HeoresFragment.view!!.findNavController().navigate(R.id.detailHeroFragment)
                }
                binding.recyclerHeroes.layoutManager = layoutManager
                binding.recyclerHeroes.adapter = adapter
            } else {
                dialog =
                    AwesomeDialog.build(requireActivity()).position(AwesomeDialog.POSITIONS.CENTER)
                        .background(R.drawable.loading_back)
                dialog.title("Error")
                dialog.body("Tiempo de consulta excedido")
                dialog.icon(R.drawable.warning)
                dialog.show()
            }
        }
    }

    private fun onClicks(){
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0!!.isNotEmpty()) {
                    viewModel.getHeroesSearch(p0.toString(), arrayHeroes)
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        checkResult(arrayHeroes)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.clear.setOnClickListener { binding.search.setText("") }
        binding.refresh.setOnClickListener { viewModel.getHeroes(0) }
        binding.firstPage.setOnClickListener {
            viewModel.changueBackGround(requireContext(),binding.firstPage,binding.secondPage,binding.thirdPage)
            GlobalClass.offset = 0
            viewModel.getHeroes(GlobalClass.offset)
        }
        binding.secondPage.setOnClickListener {
            viewModel.changueBackGround(requireContext(),binding.secondPage,binding.firstPage,binding.thirdPage)
            GlobalClass.offset = 100
            viewModel.getHeroes(GlobalClass.offset)
        }
        binding.thirdPage.setOnClickListener {
            viewModel.changueBackGround(requireContext(),binding.thirdPage,binding.secondPage,binding.firstPage)
            GlobalClass.offset = 200
            viewModel.getHeroes(GlobalClass.offset)
        }
    }

}
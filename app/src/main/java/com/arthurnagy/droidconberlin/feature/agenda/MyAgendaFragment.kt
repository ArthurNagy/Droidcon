package com.arthurnagy.droidconberlin.feature.agenda

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.MyAgendaBinding
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.architecture.DroidconFragment
import com.arthurnagy.droidconberlin.setupToolbar

class MyAgendaFragment : DroidconFragment() {

    private lateinit var binding: MyAgendaBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_agenda, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar)

        val viewModel = getViewModel(MyAgendaViewModel::class.java)
        binding.viewModel = viewModel
    }
}
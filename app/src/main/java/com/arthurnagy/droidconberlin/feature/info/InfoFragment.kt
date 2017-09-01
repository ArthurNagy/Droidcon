package com.arthurnagy.droidconberlin.feature.info

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.InfoBinding
import com.arthurnagy.droidconberlin.architecture.DroidconFragment
import com.arthurnagy.droidconberlin.setupToolbar

class InfoFragment : DroidconFragment() {

    private lateinit var binding: InfoBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar)

        val viewModel = getViewModel(InfoViewModel::class.java)
        binding.viewModel = viewModel
    }

}
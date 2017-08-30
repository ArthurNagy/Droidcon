package com.arthurnagy.droidconberlin.feature.schedule

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.ScheduleBinding
import com.arthurnagy.droidconberlin.architecture.DroidconFragment

class ScheduleFragment : DroidconFragment() {

    private lateinit var binding: ScheduleBinding
    private val viewModel: ScheduleViewModel by lazy { getViewModel(ScheduleViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.loadSessions()
    }

}
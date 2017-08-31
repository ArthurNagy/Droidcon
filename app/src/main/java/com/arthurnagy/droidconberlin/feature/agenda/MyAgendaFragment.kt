package com.arthurnagy.droidconberlin.feature.agenda

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.MyAgendaBinding
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.architecture.DroidconFragment
import com.arthurnagy.droidconberlin.feature.shared.StickyHeaderItemDecoration
import com.arthurnagy.droidconberlin.setupToolbar

class MyAgendaFragment : DroidconFragment() {

    private lateinit var binding: MyAgendaBinding
    private val viewModel: MyAgendaViewModel by lazy { getViewModel(MyAgendaViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_agenda, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar)
        binding.viewModel = viewModel
        binding.recyclerView.addItemDecoration(object : StickyHeaderItemDecoration(context) {
            override fun isHeader(position: Int) = viewModel.isHeaderItem(position)

            override fun getHeaderTitle(position: Int) = viewModel.getHeaderItemTitle(position)
        })
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel.subscribe()
        viewModel.load()
    }
}
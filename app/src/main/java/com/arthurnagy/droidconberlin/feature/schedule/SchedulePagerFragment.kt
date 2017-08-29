package com.arthurnagy.droidconberlin.feature.schedule

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.SchedulePagerBinding
import com.arthurnagy.droidconberlin.architecture.DroidconFragment
import com.arthurnagy.droidconberlin.setupToolbar

class SchedulePagerFragment : DroidconFragment() {

    private lateinit var binding: SchedulePagerBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_pager, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar)

        val viewModel = getViewModel(SchedulePagerViewModel::class.java)
        binding.viewModel = viewModel

        binding.pager.adapter = SchedulePagerAdapter(context, childFragmentManager)
        binding.tab.setupWithViewPager(binding.pager)
    }

    class SchedulePagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return ScheduleFragment()
        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence {
            return context.getString(R.string.schedule_tab_title, 4 + position)
        }

    }


}
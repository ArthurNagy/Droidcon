package com.arthurnagy.droidcon.feature.schedule

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.SchedulePagerBinding
import com.arthurnagy.droidcon.architecture.DroidconFragment
import com.arthurnagy.droidcon.feature.schedule.list.ScheduleFragment
import com.arthurnagy.droidcon.util.setupToolbar
import java.text.SimpleDateFormat
import java.util.*

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

        binding.pager.adapter = SchedulePagerAdapter(childFragmentManager)
        binding.tab.setupWithViewPager(binding.pager)
    }

    class SchedulePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        companion object {
            private val DATE_PATTERN = "MMMM d"
            private val TITLE_DATES = arrayOf(Date(1504483200000), Date(1504569600000))
        }

        override fun getItem(position: Int): Fragment {
            return ScheduleFragment.newInstance(TITLE_DATES[position].time)
        }

        override fun getCount(): Int = 2

        override fun getPageTitle(position: Int): CharSequence {
            return SimpleDateFormat(DATE_PATTERN, Locale.getDefault()).format(TITLE_DATES[position])
        }

    }


}
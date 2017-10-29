package com.arthurnagy.droidcon.feature.schedule.list

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.ScheduleBinding
import com.arthurnagy.droidcon.architecture.DroidconFragment
import com.arthurnagy.droidcon.feature.session.SessionDetailActivity
import com.arthurnagy.droidcon.feature.shared.StickyHeaderItemDecoration
import com.arthurnagy.droidcon.util.color
import com.arthurnagy.droidcon.util.observe

class ScheduleFragment : DroidconFragment() {

    private lateinit var binding: ScheduleBinding
    private val viewModel: ScheduleViewModel by lazy { getViewModel(ScheduleViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        arguments?.let {
            viewModel.setScheduleDate(it.getLong(SCHEDULE_DAY_TIMESTAMP))
        }

        binding.scheduleRefreshLayout.setColorSchemeColors(context.color(R.color.accent), context.color(R.color.primary))

        binding.scheduleRecycler.adapter = viewModel.adapter
        binding.scheduleRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        context?.let {
            binding.scheduleRecycler.addItemDecoration(object : StickyHeaderItemDecoration(it) {
                override fun isHeader(position: Int) = viewModel.isHeaderItem(position)

                override fun getHeaderTitle(position: Int) = viewModel.getHeaderItemTitle(position)
            })

            viewModel.clickedSession.observe { session ->
                SessionDetailActivity.start(it, session.id)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.subscribe()
        viewModel.load()
    }

    override fun onPause() {
        super.onPause()
        viewModel.unsubscribe()
    }

    companion object {
        private val SCHEDULE_DAY_TIMESTAMP = "scheduleDayTimestamp"
        @JvmStatic
        fun newInstance(dayTimestamp: Long): ScheduleFragment {
            val fragment = ScheduleFragment()
            val args = Bundle()
            args.putLong(SCHEDULE_DAY_TIMESTAMP, dayTimestamp)
            fragment.arguments = args
            return fragment
        }
    }

}
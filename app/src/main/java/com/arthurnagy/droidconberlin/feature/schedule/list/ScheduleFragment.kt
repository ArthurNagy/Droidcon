package com.arthurnagy.droidconberlin.feature.schedule.list

import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.ScheduleBinding
import com.arthurnagy.droidconberlin.architecture.DroidconFragment
import com.arthurnagy.droidconberlin.feature.session.SessionDetailActivity
import com.arthurnagy.droidconberlin.feature.shared.StickyHeaderItemDecoration
import com.arthurnagy.droidconberlin.util.color

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

        viewModel.setScheduleDate(arguments.getLong(SCHEDULE_DAY_TIMESTAMP))

        binding.scheduleRecycler.adapter = viewModel.adapter
        binding.scheduleRecycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.scheduleRecycler.addItemDecoration(object : StickyHeaderItemDecoration(context) {
            override fun isHeader(position: Int) = viewModel.isHeaderItem(position)

            override fun getHeaderTitle(position: Int) = viewModel.getHeaderItemTitle(position)
        })

        binding.scheduleRefreshLayout.setColorSchemeColors(context.color(R.color.accent), context.color(R.color.primary))
        viewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    BR.sessionClick -> SessionDetailActivity.start(context, viewModel.sessionClick?.id ?: "")
                }
            }
        })
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
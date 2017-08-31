package com.arthurnagy.droidconberlin.feature.schedule.list

import android.databinding.Bindable
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.model.Session
import com.arthurnagy.droidconberlin.plusAssign
import com.arthurnagy.droidconberlin.repository.SessionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
        private val sessionRepository: SessionRepository) : DroidconViewModel() {
    private val disposables = CompositeDisposable()
    val adapter = ScheduleAdapter()
    @Bindable
    var sessionClick: Session? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.sessionClick)
        }
    private var scheduleDateCalendar: Calendar = Calendar.getInstance()
    @Bindable
    var swipeRefreshState = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.swipeRefreshState)
        }

    fun setScheduleDate(dateTimestamp: Long) {
        scheduleDateCalendar.time = Date(dateTimestamp)
    }

    fun subscribe() {
        disposables += sessionRepository.stream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { sessions ->
                    val sessionCalendar = Calendar.getInstance()
                    adapter.replace(sessions.filter { session ->
                        sessionCalendar.time = session.startDate
                        scheduleDateCalendar[Calendar.YEAR] == sessionCalendar[Calendar.YEAR] &&
                                scheduleDateCalendar[Calendar.MONTH] == sessionCalendar[Calendar.MONTH] &&
                                scheduleDateCalendar[Calendar.DAY_OF_MONTH] == sessionCalendar[Calendar.DAY_OF_MONTH]
                    }.sortedWith(compareBy { it.startDate }))
                }
    }

    fun load() {
        swipeRefreshState = true
        disposables += sessionRepository.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    swipeRefreshState = false
                }, {
                    swipeRefreshState = false
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun isHeaderItem(position: Int) = when (position) {
        0 -> true
        else -> {
            val previousItemCalendar = Calendar.getInstance()
            val currentItemCalendar = Calendar.getInstance()
            previousItemCalendar.time = adapter.getItem(position - 1).startDate
            currentItemCalendar.time = adapter.getItem(position).startDate
            previousItemCalendar[Calendar.HOUR] != currentItemCalendar[Calendar.HOUR] ||
                    previousItemCalendar[Calendar.MINUTE] != currentItemCalendar[Calendar.MINUTE] ||
                    (previousItemCalendar[Calendar.HOUR] != currentItemCalendar[Calendar.HOUR] &&
                            previousItemCalendar[Calendar.MINUTE] != currentItemCalendar[Calendar.MINUTE])
        }
    }

    fun onAdapterItemClicked(position: Int) {
        sessionClick = adapter.getItem(position)
    }

    fun getHeaderItemTitle(position: Int): String
            = SimpleDateFormat(STICKY_TIME_PATTERN, Locale.getDefault()).format(adapter.getItem(position).startDate)

    companion object {
        private const val STICKY_TIME_PATTERN = "hh:mm a"
    }
}
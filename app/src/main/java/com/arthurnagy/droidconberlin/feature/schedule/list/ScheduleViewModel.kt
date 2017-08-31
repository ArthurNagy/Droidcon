package com.arthurnagy.droidconberlin.feature.schedule.list

import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
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
    private var scheduleDateCalendar: Calendar = Calendar.getInstance()

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
        disposables += sessionRepository.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { error ->
                    println(error.message)
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun isHeaderItem(position: Int): Boolean = when (position) {
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

    fun getHeaderItemTitle(position: Int): String
            = SimpleDateFormat(STICKY_TIME_PATTERN, Locale.getDefault()).format(adapter.getItem(position).startDate)

    companion object {
        private const val STICKY_TIME_PATTERN = "hh:mm a"
    }
}
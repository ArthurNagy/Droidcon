package com.arthurnagy.droidcon.feature.schedule.list

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.util.Log
import com.arthurnagy.droidcon.architecture.repository.SessionRepository
import com.arthurnagy.droidcon.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidcon.feature.shared.ViewModelBoundAdapter
import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
        private val sessionRepository: SessionRepository) : DroidconViewModel() {
    val adapter = ScheduleAdapter()
    val clickedSession = MutableLiveData<Session>()
    val swipeRefreshState = ObservableBoolean()
    private var scheduleDateCalendar: Calendar = Calendar.getInstance()

    init {
        adapter.setItemClickListener(object : ViewModelBoundAdapter.AdapterItemClickListener {
            override fun onItemClicked(position: Int) {
                clickedSession.value = adapter.getItem(position)
            }
        })

        adapter.setItemSavedClickListener { position ->
            val savedSession = adapter.getItem(position).apply { isSaved = !isSaved }
            disposables += sessionRepository.update(savedSession)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ session ->
                        adapter.updateItem(position, session)
                        Log.d(TAG, "${savedSession.title} added to my agenda")
                    }, {
                        Log.d(TAG, "failed to add ${savedSession.title} to my agenda")
                    })
        }
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

    fun unsubscribe() {
        disposables.clear()
    }

    fun load() {
        swipeRefreshState.set(true)
        disposables += sessionRepository.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "sessionRepository.get:success")
                    swipeRefreshState.set(false)
                }, {
                    Log.d(TAG, "sessionRepository.get:error: ${it.message}")
                    swipeRefreshState.set(false)
                })
    }

    fun refresh() {
        swipeRefreshState.set(true)
        disposables += sessionRepository.refresh()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d(TAG, "sessionRepository.refresh:success")
                    swipeRefreshState.set(false)
                }, {
                    Log.d(TAG, "sessionRepository.refresh:error: ${it.message}")
                    swipeRefreshState.set(false)
                })
    }

    fun isHeaderItem(position: Int) = when (position) {
        -1, -2 -> false
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

    fun getHeaderItemTitle(position: Int): String = when (position) {
        -1, -2 -> ""
        else -> SimpleDateFormat(STICKY_TIME_PATTERN, Locale.getDefault()).format(adapter.getItem(position).startDate)
    }

    companion object {
        private const val STICKY_TIME_PATTERN = "hh:mm a"
        private const val TAG = "ScheduleViewModel"
    }
}
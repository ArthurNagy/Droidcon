package com.arthurnagy.droidconberlin.feature.schedule.list

import android.databinding.Bindable
import android.util.Log
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.SharedPreferencesManager
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.feature.shared.ViewModelBoundAdapter
import com.arthurnagy.droidconberlin.model.Session
import com.arthurnagy.droidconberlin.repository.SessionRepository
import com.arthurnagy.droidconberlin.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
        private val sessionRepository: SessionRepository,
        private val sharedPreferencesManager: SharedPreferencesManager) : DroidconViewModel() {
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

    init {
        adapter.setItemClickListener(object : ViewModelBoundAdapter.AdapterItemClickListener {
            override fun onItemClicked(position: Int) {
                sessionClick = adapter.getItem(position)
            }
        })

        adapter.setItemSavedClickListener { position ->
            val savedSession = adapter.getItem(position).apply { isSaved = !isSaved }
            if (savedSession.isSaved) {
                sharedPreferencesManager.saveSessionId(savedSession.id)
            } else {
                sharedPreferencesManager.deleteSessionId(savedSession.id)
            }
            disposables += sessionRepository.save(savedSession)
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

    fun refresh() {
        swipeRefreshState = true
        disposables += sessionRepository.refresh()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    swipeRefreshState = false
                }, {
                    swipeRefreshState = false
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
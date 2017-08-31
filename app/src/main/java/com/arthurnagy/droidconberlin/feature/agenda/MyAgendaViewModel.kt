package com.arthurnagy.droidconberlin.feature.agenda

import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.feature.agenda.list.MyAgendaAdapter
import com.arthurnagy.droidconberlin.plusAssign
import com.arthurnagy.droidconberlin.repository.SessionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MyAgendaViewModel @Inject constructor(
        private val sessionRepository: SessionRepository) : DroidconViewModel() {
    private val disposables = CompositeDisposable()
    val adapter = MyAgendaAdapter()

    fun subscribe() {
        disposables += sessionRepository.stream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { sessions ->
                    adapter.replace(sessions.sortedWith(compareBy { it.startDate }))
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

    fun onAdapterItemClicked(position: Int) {
        //TODO
    }

    fun isHeaderItem(position: Int): Boolean = when (position) {
        0 -> true
        else -> {
            val previousItemCalendar = Calendar.getInstance()
            val currentItemCalendar = Calendar.getInstance()
            previousItemCalendar.time = adapter.getItem(position - 1).startDate
            currentItemCalendar.time = adapter.getItem(position).startDate
            previousItemCalendar[Calendar.DAY_OF_MONTH] != currentItemCalendar[Calendar.DAY_OF_MONTH]
        }
    }

    fun getHeaderItemTitle(position: Int): String
            = SimpleDateFormat(STICKY_DATE_PATTERN, Locale.getDefault()).format(adapter.getItem(position).startDate)

    companion object {
        private const val STICKY_DATE_PATTERN = "MMMM d"
    }
}
package com.arthurnagy.droidconberlin.feature.agenda

import android.databinding.Bindable
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.SharedPreferencesManager
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.feature.agenda.list.MyAgendaAdapter
import com.arthurnagy.droidconberlin.feature.shared.ViewModelBoundAdapter
import com.arthurnagy.droidconberlin.model.Session
import com.arthurnagy.droidconberlin.plusAssign
import com.arthurnagy.droidconberlin.repository.SessionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MyAgendaViewModel @Inject constructor(
        private val sessionRepository: SessionRepository,
        private val sharedPreferencesManager: SharedPreferencesManager) : DroidconViewModel() {
    private val disposables = CompositeDisposable()
    val adapter = MyAgendaAdapter()

    init {
        adapter.setItemClickListener(object : ViewModelBoundAdapter.AdapterItemClickListener {
            override fun onItemClicked(position: Int) {
                sessionClick = adapter.getItem(position)
            }
        })
    }

    @Bindable
    var swipeRefreshState = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.swipeRefreshState)
        }
    @Bindable
    var sessionClick: Session? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.sessionClick)
        }

    fun subscribe() {
        disposables += sessionRepository.stream()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { sessions ->
                    val savedSessions = sharedPreferencesManager.getSavedSessionIds()
                    val mySessions = sessions.filter {
                        Session.isIntermission(it) || savedSessions.contains(it.id)
                    }
                    adapter.replace(mySessions.sortedWith(compareBy { it.startDate }))
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
package com.arthurnagy.droidconberlin.feature.schedule

import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.plusAssign
import com.arthurnagy.droidconberlin.repository.SessionRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ScheduleViewModel @Inject constructor(
        private val sessionRepository: SessionRepository) : DroidconViewModel() {
    private val disposables = CompositeDisposable()

    fun loadSessions() {
        disposables += sessionRepository.get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ sessions ->
                    sessions.forEachIndexed { index, session ->
                        println("$index, ${session.title}")
                    }
                }, { error ->
                    println(error.message)
                })
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}
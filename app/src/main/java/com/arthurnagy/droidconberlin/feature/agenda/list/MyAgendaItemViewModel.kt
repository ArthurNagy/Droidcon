package com.arthurnagy.droidconberlin.feature.agenda.list

import android.databinding.*
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.model.Session
import com.arthurnagy.droidconberlin.util.dependsOn
import java.text.SimpleDateFormat
import java.util.*

class MyAgendaItemViewModel : DroidconViewModel() {

    val scheduleSession: ObservableField<Session> = ObservableField()
    val sessionShortInfo: ObservableField<String> = ObservableField()
    val sessionIntermissionFlag: ObservableBoolean = ObservableBoolean()

    init {
        sessionShortInfo.dependsOn(scheduleSession, {
            val start = SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(it.startDate)
            val end = SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(it.endDate)
            "$start - $end / ${it.room} stage"
        })
        sessionIntermissionFlag.dependsOn(scheduleSession, { Session.isIntermission(it) })
    }

    companion object {
        private const val TIME_PATTERN = "hh:mm a"
    }
}
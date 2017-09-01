package com.arthurnagy.droidconberlin.feature.agenda.list

import android.annotation.SuppressLint
import android.databinding.Bindable
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.model.Session
import java.text.SimpleDateFormat
import java.util.*

class MyAgendaItemViewModel : DroidconViewModel() {
    @Bindable
    var scheduleSession: Session? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.scheduleSession)
        }

    @SuppressLint("SimpleDateFormat")
    @Bindable(PROPERTY_SESSION)
    fun getShortInfo(): String {
        scheduleSession?.let { session ->
            val start = SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(session.startDate)
            val end = SimpleDateFormat(TIME_PATTERN, Locale.getDefault()).format(session.endDate)
            return "$start - $end / ${session.room} stage"
        }
        return ""
    }

    @Bindable(PROPERTY_SESSION)
    fun getIntermissionFlag() = Session.isIntermission(scheduleSession)


    companion object {
        private const val PROPERTY_SESSION = "scheduleSession"
        private const val TIME_PATTERN = "hh:mm a"

    }
}
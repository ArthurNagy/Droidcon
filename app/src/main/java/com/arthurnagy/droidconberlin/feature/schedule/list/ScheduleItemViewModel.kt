package com.arthurnagy.droidconberlin.feature.schedule.list

import android.annotation.SuppressLint
import android.databinding.Bindable
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.model.Session
import java.util.*


class ScheduleItemViewModel : DroidconViewModel() {

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
            val durationCalendar = Calendar.getInstance().apply {
                time = Date(session.endDate.time - session.startDate.time)
            }
            durationCalendar.clear(Calendar.ZONE_OFFSET)
            println("hours: ${durationCalendar[Calendar.HOUR]}, minutes: ${durationCalendar[Calendar.MINUTE]}")
            return " / ${session.room} stage"
        }
        return ""
    }

    @Bindable(PROPERTY_SESSION)
    fun getTerms(): String {
        scheduleSession?.let {
            return it.terms?.joinToString(separator = " / ", transform = { term -> term.name }) ?: ""
        }
        return ""
    }

    @Bindable(PROPERTY_SESSION)
    fun getIntermissionFlag() = scheduleSession?.id?.contains(Session.INTERMISSION)

    companion object {
        private const val PROPERTY_SESSION = "scheduleSession"
    }

}
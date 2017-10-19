package com.arthurnagy.droidconberlin.feature.schedule.list

import android.annotation.SuppressLint
import android.databinding.Bindable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.annotation.DrawableRes
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.model.Session
import com.arthurnagy.droidconberlin.util.dependsOn
import java.util.*


class ScheduleItemViewModel : DroidconViewModel() {

    val scheduleSession = ObservableField<Session>()
    val sessionShortInfo = ObservableField<String>().dependsOn(scheduleSession, { session ->
        val durationCalendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT")).apply {
            time = Date(session.endDate.time - session.startDate.time)
        }
        val hours = durationCalendar[Calendar.HOUR]
        val minutes = durationCalendar[Calendar.MINUTE]
        var duration = ""
        if (hours != 0) {
            duration += "$hours h "
        }
        if (minutes != 0) {
            duration += "$minutes min "
        }
        "$duration/ ${session.room} stage"
    })
    val sessionIntermissionFlag = ObservableBoolean().dependsOn(scheduleSession, { Session.isIntermission(it) })
    val savedStateIcon = ObservableInt().dependsOn(scheduleSession, { if (it.isSaved) R.drawable.ic_check_box_24dp else R.drawable.ic_add_box_24dp })

    companion object {
        private const val PROPERTY_SESSION = "scheduleSession"
    }
}
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
            return "$duration/ ${session.room} stage"
        }
        return ""
    }

    @Bindable(PROPERTY_SESSION)
    fun getIntermissionFlag() = scheduleSession?.id?.contains(Session.INTERMISSION)

    companion object {
        private const val PROPERTY_SESSION = "scheduleSession"
    }
}
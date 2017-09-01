package com.arthurnagy.droidconberlin.feature.schedule.list

import android.annotation.SuppressLint
import android.databinding.Bindable
import android.support.annotation.DrawableRes
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.R
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
    fun getIntermissionFlag() = Session.isIntermission(scheduleSession)

    @Bindable(PROPERTY_SESSION)
    @DrawableRes
    fun getSavedStateIcon(): Int {
        scheduleSession?.let {
            return if (it.isSaved) R.drawable.ic_check_box_24dp else R.drawable.ic_add_box_24dp
        }
        return R.drawable.ic_add_box_24dp
    }

    companion object {
        private const val PROPERTY_SESSION = "scheduleSession"
    }
}
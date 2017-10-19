package com.arthurnagy.droidconberlin.feature.session

import android.databinding.Bindable
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.support.annotation.DrawableRes
import android.text.Html
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.model.Session
import com.arthurnagy.droidconberlin.repository.SessionRepository
import com.arthurnagy.droidconberlin.util.dependsOn
import com.arthurnagy.droidconberlin.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SessionDetailViewModel @Inject constructor(
        private val sessionRepository: SessionRepository) : DroidconViewModel() {
    val session = ObservableField<Session>()
    val sessionDateInterval = ObservableField<String>().dependsOn(session, {
        "${SimpleDateFormat(START_DATE_PATTERN, Locale.getDefault()).format(it.startDate)} - ${SimpleDateFormat(END_DATE_PATTERN, Locale.getDefault()).format(it.endDate)}"
    })
    val sessionDescription = ObservableField<String>().dependsOn(session, {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(it.description)
        }.toString()
    })
    val sessionStateIcon = ObservableInt().dependsOn(session, { if (it.isSaved) R.drawable.ic_check_box_24dp else R.drawable.ic_add_box_24dp })

    fun setupSession(sessionId: String) {
        disposables += sessionRepository.get(sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ session ->
                    this.session.set(session)
                }, {

                })
    }

    fun updateSaveState() {
        session.get()?.let { session ->
            session.apply { isSaved = !isSaved }
            if (session.isSaved) {
                TODO()
            } else {
                TODO()
            }
            disposables += sessionRepository.save(session)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({}, {})
        }

    }

    companion object {
        private const val SESSION = "session"
        private const val END_DATE_PATTERN = "hh:mm a"
        private const val START_DATE_PATTERN = "EEE, MMM, $END_DATE_PATTERN"
        private const val NA = "N/A"
    }

}
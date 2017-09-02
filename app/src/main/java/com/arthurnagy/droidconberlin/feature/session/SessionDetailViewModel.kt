package com.arthurnagy.droidconberlin.feature.session

import android.databinding.Bindable
import android.support.annotation.DrawableRes
import android.text.Html
import com.arthurnagy.droidconberlin.BR
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.SharedPreferencesManager
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.model.Session
import com.arthurnagy.droidconberlin.repository.SessionRepository
import com.arthurnagy.droidconberlin.util.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import javax.inject.Inject

class SessionDetailViewModel @Inject constructor(
        private val sessionRepository: SessionRepository,
        private val sharedPreferencesManager: SharedPreferencesManager) : DroidconViewModel() {
    @Bindable
    var session: Session? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.session)
        }

    fun setupSession(sessionId: String) {
        disposables += sessionRepository.get(sessionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ session ->
                    if (sharedPreferencesManager.isSavedSession(session.id)) session.isSaved = true
                    this.session = session
                }, {

                })
    }

    @Bindable(SESSION)
    fun getDateInterval(): String {
        session?.let {
            return "${SimpleDateFormat(START_DATE_PATTERN).format(it.startDate)} - ${SimpleDateFormat(END_DATE_PATTERN).format(it.endDate)}"
        }
        return NA
    }

    @Bindable(SESSION)
    fun getSessionDescription(): String {
        session?.let {
            return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(it.description)
            }.toString()
        }
        return NA
    }

    @Bindable(SESSION)
    @DrawableRes
    fun getSessionStateIcon(): Int {
        session?.let {
            return if (it.isSaved) R.drawable.ic_check_box_24dp else R.drawable.ic_add_box_24dp
        }
        return R.drawable.ic_add_box_24dp
    }

    fun updateSaveState() {
        session?.let { session ->
            session.isSaved = !session.isSaved
            notifyPropertyChanged(BR.session)
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
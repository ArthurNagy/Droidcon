package com.arthurnagy.droidconberlin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.arthurnagy.droidconberlin.feature.session.SessionDetailActivity
import com.arthurnagy.droidconberlin.repository.SessionRepository
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class SessionJobService : JobService() {

    @Inject lateinit var sessionRepository: SessionRepository
    private lateinit var notificationManager: NotificationManager
    var disposable: Disposable? = null

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        disposable?.dispose()
        return false
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        job?.let {
            val sessionId: String = job.tag
            disposable = sessionRepository.get()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ sessions ->
                        val filteredSessions = sessions.filter { (id) -> sessionId == id }
                        val session = filteredSessions[0]
                        @Suppress("DEPRECATION")
                        val builder = (if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1)
                            NotificationCompat.Builder(this, NotificationChannel.DEFAULT_CHANNEL_ID) else
                            NotificationCompat.Builder(this))
                                .setSmallIcon(R.drawable.ic_event_white_24dp)
                                .setContentTitle(getString(R.string.app_name))
                                .setAutoCancel(true)
                                .setContentText(getString(R.string.session_is_about_to_start))
                                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                .setStyle(NotificationCompat.BigTextStyle().bigText(getString(R.string.session_notification_message, session.title, session.room.toString())))
                                .setContentIntent(PendingIntent.getActivity(applicationContext, SESSION_REQ, SessionDetailActivity.getStarterIntent(this, session.id), PendingIntent.FLAG_CANCEL_CURRENT))

                        notificationManager.notify(sessionId.toInt(), builder.build())
                    }, {})
        }
        return false
    }

    companion object {
        private const val SESSION_REQ = 33
    }

}
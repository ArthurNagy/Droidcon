package com.arthurnagy.droidconberlin.feature.session

import android.content.Context
import android.content.Intent
import com.arthurnagy.droidconberlin.architecture.DroidconActivity

class SessionDetailActivity : DroidconActivity() {
    companion object {
        private const val SESSION_ID = "sessionId"

        fun getStartIntent(context: Context, sessionId: String): Intent =
                Intent(context, SessionDetailActivity::class.java).putExtra(SESSION_ID, sessionId)
    }
}
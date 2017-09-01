package com.arthurnagy.droidconberlin.feature.session

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.SessionDetailBinding
import com.arthurnagy.droidconberlin.architecture.DroidconActivity

class SessionDetailActivity : DroidconActivity() {
    companion object {
        private const val SESSION_ID = "sessionId"

        fun getStartIntent(context: Context, sessionId: String): Intent =
                Intent(context, SessionDetailActivity::class.java).putExtra(SESSION_ID, sessionId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SessionDetailBinding>(this, R.layout.activity_session_detail)
        binding.viewModel = getViewModel(SessionDetailViewModel::class.java)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
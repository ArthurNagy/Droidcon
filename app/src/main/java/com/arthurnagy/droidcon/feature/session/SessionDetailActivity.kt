package com.arthurnagy.droidcon.feature.session

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arthurnagy.droidcon.BuildConfig
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.SessionDetailBinding
import com.arthurnagy.droidcon.SpeakerBinding
import com.arthurnagy.droidcon.architecture.DroidconActivity
import com.arthurnagy.droidcon.util.observe
import com.arthurnagy.droidcon.util.openUrl

class SessionDetailActivity : DroidconActivity() {

    val viewModel: SessionDetailViewModel by lazy { getViewModel(SessionDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SessionDetailBinding>(this, R.layout.activity_session_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.viewModel = viewModel
        viewModel.setupSession(intent.getStringExtra(SESSION_ID))

        viewModel.session.observe {
            binding.container.removeViews(STATIC_VIEW_COUNT, binding.container.childCount - STATIC_VIEW_COUNT)
            it.speakers?.forEach { (name, url) ->
                val speakerBinding = SpeakerBinding.inflate(layoutInflater)
                speakerBinding.speakerName = name
                binding.container.addView(speakerBinding.root)
                speakerBinding.root.setOnClickListener { openUrl(BuildConfig.DROIDCON_URL + url) }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.session_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.share -> {
            startActivity(Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "${viewModel.session.get().title} ${BuildConfig.DROIDCON_URL + viewModel.session.get().url}")
            }, getString(R.string.share_via)))
            true
        }
        R.id.url -> {
            viewModel.session.get().let {
                openUrl(BuildConfig.DROIDCON_URL + it.url)
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        private const val SESSION_ID = "sessionId"
        private const val STATIC_VIEW_COUNT = 4

        @JvmStatic
        fun getStarterIntent(context: Context, sessionId: String): Intent {
            return Intent(context, SessionDetailActivity::class.java).putExtra(SESSION_ID, sessionId)
        }

        @JvmStatic
        fun start(context: Context, sessionId: String) {
            context.startActivity(Intent(context, SessionDetailActivity::class.java).putExtra(SESSION_ID, sessionId))
        }
    }

}
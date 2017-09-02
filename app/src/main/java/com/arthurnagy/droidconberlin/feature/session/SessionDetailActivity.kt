package com.arthurnagy.droidconberlin.feature.session

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.arthurnagy.droidconberlin.*
import com.arthurnagy.droidconberlin.architecture.DroidconActivity
import com.arthurnagy.droidconberlin.util.openUrl

class SessionDetailActivity : DroidconActivity() {

    val viewModel: SessionDetailViewModel by lazy { getViewModel(SessionDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<SessionDetailBinding>(this, R.layout.activity_session_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.viewModel = viewModel
        viewModel.setupSession(intent.getStringExtra(SESSION_ID))

        viewModel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId == BR.session) {
                    viewModel.session?.speakers?.forEach { (name, url) ->
                        val speakerBinding = SpeakerBinding.inflate(layoutInflater)
                        speakerBinding.speakerName = name
                        binding.container.addView(speakerBinding.root)
                        speakerBinding.root.setOnClickListener { openUrl(BuildConfig.DROIDCON_URL + url) }
                    }
                }
            }
        })
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
                putExtra(Intent.EXTRA_SUBJECT, "${viewModel.session?.title} ${BuildConfig.DROIDCON_URL + viewModel.session?.url}")
            }, getString(R.string.share_via)))
            true
        }
        R.id.url -> {
            viewModel.session?.let {
                openUrl(BuildConfig.DROIDCON_URL + it.url)
            }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        private const val SESSION_ID = "sessionId"

        fun start(context: Context, sessionId: String) {
            context.startActivity(Intent(context, SessionDetailActivity::class.java).putExtra(SESSION_ID, sessionId))
        }
    }

}
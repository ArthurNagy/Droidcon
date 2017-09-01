package com.arthurnagy.droidconberlin.feature.info

import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.InfoBinding
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.architecture.DroidconFragment
import com.arthurnagy.droidconberlin.util.color
import com.arthurnagy.droidconberlin.util.setupToolbar

class InfoFragment : DroidconFragment() {

    private lateinit var binding: InfoBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar)

        val viewModel = getViewModel(InfoViewModel::class.java)
        binding.viewModel = viewModel

        binding.codeOfConduct.setOnClickListener { openUrl("https://droidcon.de/en/berlin/17/code-conduct") }
        binding.venue.setOnClickListener { openUrl("https://droidcon.de/en/berlin/17/venue") }
        binding.viewOnGitHub.setOnClickListener { openUrl(GIT_HUB_URL) }
        binding.share.setOnClickListener {
            startActivity(Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, GIT_HUB_URL)
            }, context.getString(R.string.share_via)))

        }
    }

    private fun openUrl(url: String) = CustomTabsIntent.Builder()
            .setToolbarColor(context.color(R.color.primary))
            .setShowTitle(true)
            .build().launchUrl(context, Uri.parse(url))

    companion object {
        private const val GIT_HUB_URL = "https://github.com/ArthurNagy/DroidconBerlin"
    }
}
package com.arthurnagy.droidcon.feature.info

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidcon.BuildConfig
import com.arthurnagy.droidcon.InfoBinding
import com.arthurnagy.droidcon.R
import com.arthurnagy.droidcon.architecture.DroidconFragment
import com.arthurnagy.droidcon.util.openUrl
import com.arthurnagy.droidcon.util.setupToolbar

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

        binding.codeOfConduct.setOnClickListener { context.openUrl(BuildConfig.DROIDCON_URL + "/en/berlin/17/code-conduct") }
        binding.venue.setOnClickListener { context.openUrl(BuildConfig.DROIDCON_URL + "/en/berlin/17/venue") }
        binding.viewOnGitHub.setOnClickListener { context.openUrl(GIT_HUB_URL) }
        binding.share.setOnClickListener {
            startActivity(Intent.createChooser(Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, GIT_HUB_URL)
            }, context.getString(R.string.share_via)))

        }
    }

    companion object {
        private const val GIT_HUB_URL = "https://github.com/ArthurNagy/DroidconBerlin"
    }
}
package com.arthurnagy.droidconberlin.feature.info

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arthurnagy.droidconberlin.BuildConfig
import com.arthurnagy.droidconberlin.InfoBinding
import com.arthurnagy.droidconberlin.R
import com.arthurnagy.droidconberlin.architecture.DroidconFragment
import com.arthurnagy.droidconberlin.util.openUrl
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

        binding.codeOfConduct.setOnClickListener { context.openUrl(BuildConfig.DROIDCON_URL + "code-conduct") }
        binding.venue.setOnClickListener { context.openUrl(BuildConfig.DROIDCON_URL + "venue") }
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
package com.arthurnagy.droidconberlin.feature.session

import android.databinding.ObservableField
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.model.Session
import javax.inject.Inject

class SessionDetailViewModel @Inject constructor() : DroidconViewModel() {
    val session = ObservableField<Session>()
}
package com.arthurnagy.droidconberlin.feature.agenda.list

import android.databinding.ObservableField
import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import com.arthurnagy.droidconberlin.model.Session

class MyAgendaItemViewModel : DroidconViewModel() {
    val session = ObservableField<Session>()

    //TODO: Set the proper values.
}
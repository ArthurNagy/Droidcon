package com.arthurnagy.droidconberlin.feature.agenda

import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import javax.inject.Inject

class MyAgendaViewModel @Inject constructor() : DroidconViewModel() {
    val adapter = MyAgendaAdapter()
    val items = mutableListOf<String>()

    init {
        for (category in 1..30) {
            (1..20).mapTo(items) { "Item $it in category $category" }
        }
        adapter.setItems(items.filter { it is String })
    }
}
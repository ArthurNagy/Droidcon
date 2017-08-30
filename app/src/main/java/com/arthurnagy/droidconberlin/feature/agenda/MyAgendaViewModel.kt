package com.arthurnagy.droidconberlin.feature.agenda

import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import javax.inject.Inject

class MyAgendaViewModel @Inject constructor() : DroidconViewModel() {
    val adapter = MyAgendaAdapter()

    init {
        val items = mutableListOf<Any>()
        for (category in 0..50) {
            (0..20).mapTo(items) { if (it == 0) category else "Item $it in category $category" }
        }
        adapter.setItems(items)
    }
}
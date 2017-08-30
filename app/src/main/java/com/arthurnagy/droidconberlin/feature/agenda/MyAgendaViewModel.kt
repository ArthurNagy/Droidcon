package com.arthurnagy.droidconberlin.feature.agenda

import com.arthurnagy.droidconberlin.architecture.viewmodel.DroidconViewModel
import javax.inject.Inject

class MyAgendaViewModel @Inject constructor() : DroidconViewModel() {
    val adapter = MyAgendaAdapter()
    val items = mutableListOf<String>()

    init {
        for (category in 1..CATEGORY_COUNT) {
            (1..ITEM_COUNT_PER_CATEGORY).mapTo(items) { "Item $it in category $category" }
        }
        adapter.setItems(items.filter { it is String })
    }

    companion object {
        const val CATEGORY_COUNT = 30
        const val ITEM_COUNT_PER_CATEGORY = 20
    }
}
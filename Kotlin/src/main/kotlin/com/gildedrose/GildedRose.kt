package com.gildedrose

import com.gildedrose.updater.ItemUpdaterProvider

class GildedRose(private val items: List<Item>) {

    fun updateItems() {
        items.forEach {
            ItemUpdaterProvider.findUpdaterFor(it).update(it)
        }
    }
}
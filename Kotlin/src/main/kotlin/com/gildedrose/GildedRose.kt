package com.gildedrose

import com.gildedrose.updater.ItemUpdaterProvider

class GildedRose(private val items: List<Item>) {
    fun updateItems() {
        items.forEach {
            ItemUpdaterProvider.updaterFor(it).update(it)
        }
    }
}
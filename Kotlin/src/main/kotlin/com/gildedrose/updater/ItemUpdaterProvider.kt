package com.gildedrose.updater

import com.gildedrose.Item
import com.gildedrose.updater.ItemTypes.AGED_BRIE
import com.gildedrose.updater.ItemTypes.BACKSTAGE_PASSES
import com.gildedrose.updater.ItemTypes.CONJURED
import com.gildedrose.updater.ItemTypes.SULFURAS

object ItemTypes {

    const val AGED_BRIE = "Aged Brie"
    const val SULFURAS = "Sulfuras, Hand of Ragnaros"
    const val BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert"
    const val CONJURED = "Conjured Mana Cake"
}

object ItemUpdaterProvider {

    private val itemTypeToUpdater: Map<String, ItemUpdater> by lazy {
        mapOf(
            AGED_BRIE to QualityImprovingUpdater,
            SULFURAS to NoOpUpdater,
            BACKSTAGE_PASSES to BackstagePassQualityUpdater,
            CONJURED to QualityDegradingUpdater(2)
        )
    }

    fun updaterFor(item: Item): ItemUpdater =
        itemTypeToUpdater[item.name] ?: QualityDegradingUpdater()
}
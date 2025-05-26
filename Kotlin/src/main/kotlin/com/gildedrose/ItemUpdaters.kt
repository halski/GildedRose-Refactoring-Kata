package com.gildedrose

interface ItemUpdater {

    fun update(item: Item)
}

abstract class BaseItemUpdater : ItemUpdater {
    companion object {

        const val MIN_QUALITY = 0
        const val MAX_QUALITY = 50
//        const val SULFURAS_ITEM_NAME = "Sulfuras, Hand of Ragnaros"
//        const val BRIE_ITEM_NAME = "Aged Brie"
//        const val BACKSTAGE_PASSES_ITEM_NAME = "Backstage passes to a TAFKAL80ETC concert"
//        const val CONJURED_ITEM_NAME = "Conjured Mana Cake"
    }

    protected fun Item.isExpired() = sellIn < 0

    protected fun Item.decreaseQuality(decreaseBy: Int = 1) {
        quality = maxOf(MIN_QUALITY, quality - decreaseBy)
    }

    protected fun Item.increaseQuality(increaseBy: Int = 1) {
        quality = minOf(MAX_QUALITY, quality + increaseBy)
    }

    protected fun Item.advanceByADay() {
        sellIn -= 1
    }

    protected fun Item.decreaseQualityToZero() {
        quality = 0
    }
}

object QualityImprovingUpdater : BaseItemUpdater() {

    override fun update(item: Item) {
        item.advanceByADay()
        item.increaseQuality(if (item.isExpired()) 2 else 1)
    }
}

class QualityDegradingUpdater(private val decreaseBy: Int = 1) : BaseItemUpdater() {

    override fun update(item: Item) {
        item.advanceByADay()
        item.decreaseQuality(if (item.isExpired()) 2 * this.decreaseBy else this.decreaseBy)
    }
}

object BackstagePassQualityUpdater : BaseItemUpdater() {

    override fun update(item: Item) {
        if (item.isExpired()) {
            item.decreaseQualityToZero()
        } else {
            item.increaseQuality(determineIncrease(item.sellIn))
        }
    }

    private fun determineIncrease(sellIn: Int): Int =
        when {
            sellIn < 5 -> 3
            sellIn < 10 -> 2
            else -> 1
        }
}

object NoOpUpdater : ItemUpdater {
    override fun update(item: Item) {
        //NO UPDATE
    }
}
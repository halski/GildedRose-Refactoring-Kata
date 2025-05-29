package com.gildedrose.updater

import com.gildedrose.Item

interface ItemUpdater {

    fun update(item: Item) {
        updateExpiry(item)
        updateQuality(item)
    }

    fun updateExpiry(item: Item)
    fun updateQuality(item: Item)
}

abstract class BaseItemUpdater : ItemUpdater {
    companion object {

        const val MIN_QUALITY = 0
        const val MAX_QUALITY = 50
    }

    override fun updateExpiry(item: Item) {
        item.advanceByADay()
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

    override fun updateQuality(item: Item) {
        item.increaseQuality(if (item.isExpired()) 2 else 1)
    }
}

open class QualityDegradingUpdater(private val decreaseBy: Int = 1) : BaseItemUpdater() {

    override fun updateQuality(item: Item) {
        item.decreaseQuality(if (item.isExpired()) 2 * this.decreaseBy else this.decreaseBy)
    }
}

object QualityDegradingByOneUpdater:  QualityDegradingUpdater(1)
object QualityDegradingByTwoUpdater:  QualityDegradingUpdater(2)

object BackstagePassQualityUpdater : BaseItemUpdater() {

    override fun updateQuality(item: Item) {
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

    override fun updateQuality(item: Item) {
        //NO OP
    }

    override fun updateExpiry(item: Item) {
        //NO OP
    }
}
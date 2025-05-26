package com.gildedrose

open class Item(
    var name: String,
    var sellIn: Int,
    var quality: Int,
) {

    override fun toString(): String = "$name, $sellIn, $quality"
}

private const val MIN_QUALITY = 0
private const val MAX_QUALITY = 50

fun Item.update() {
    updateSellIn()
    updateQuality()
}

private fun Item.updateSellIn() {
    when (name) {
        SULFURAS_ITEM_NAME -> {}
        else -> advanceByADay()
    }
}

private fun Item.updateQuality() {
    when (name) {
        SULFURAS_ITEM_NAME -> {}
        BRIE_ITEM_NAME -> qualityImprovingUpdate()
        BACKSTAGE_PASSES_ITEM_NAME -> backstagePassQualityUpdate()
        CONJURED_ITEM_NAME -> qualityDegradingUpdate(2)
        else -> qualityDegradingUpdate()
    }
}

private fun Item.backstagePassQualityUpdate() {
    if (isExpired()) {
        decreaseQualityToZero()
    } else {
        increaseQuality(determineBackstagePassIncrease())
    }
}

private fun Item.determineBackstagePassIncrease(): Int =
    when {
        sellIn < 5 -> 3
        sellIn < 10 -> 2
        else -> 1
    }

private fun Item.qualityImprovingUpdate() {
    val increaseBy = if (isExpired()) 2 else 1
    increaseQuality(increaseBy)
}

private fun Item.qualityDegradingUpdate(decrease: Int = 1) {
    val decreaseBy = if (isExpired()) 2 * decrease else decrease
    decreaseQuality(decreaseBy)
}

private fun Item.isExpired() = sellIn < 0

private fun Item.decreaseQuality(decreaseBy: Int = 1) {
    quality = maxOf(MIN_QUALITY, quality - decreaseBy)
}

private fun Item.increaseQuality(increaseBy: Int = 1) {
    quality = minOf(MAX_QUALITY, quality + increaseBy)
}

private fun Item.advanceByADay() {
    sellIn -= 1
}

private fun Item.decreaseQualityToZero() {
    quality = 0
}
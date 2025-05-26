package com.gildedrose

const val BACKSTAGE_PASSES_ITEM_NAME = "Backstage passes to a TAFKAL80ETC concert"
const val SULFURAS_ITEM_NAME = "Sulfuras, Hand of Ragnaros"
const val BRIE_ITEM_NAME = "Aged Brie"
const val CONJURED_ITEM_NAME = "Conjured Mana Cake"

class GildedRose(private val items: List<Item>) {

    fun updateItems() {
        items.forEach { it.update() }
    }
}

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
        BRIE_ITEM_NAME -> {
            val increaseBy = if (isExpired()) 2 else 1
            increaseQuality(increaseBy)
        }

        BACKSTAGE_PASSES_ITEM_NAME -> {
            if (isExpired()) {
                decreaseQualityToZero()
            } else {
                val increaseBy = when {
                    sellIn < 5 -> 3
                    sellIn < 10 -> 2
                    else -> 1
                }
                increaseQuality(increaseBy)
            }
        }

        SULFURAS_ITEM_NAME -> {}

        CONJURED_ITEM_NAME -> {
            val decreaseBy = if (isExpired()) 4 else 2
            decreaseQuality(decreaseBy)
        }

        else -> {
            val decreaseBy = if (isExpired()) 2 else 1
            decreaseQuality(decreaseBy)
        }
    }
}

private const val MAX_QUALITY = 50

private fun Item.isExpired() = sellIn < 0

private fun Item.decreaseQuality(decreaseBy: Int = 1) {
    if (quality - decreaseBy < 0) quality = 0
    else quality -= decreaseBy
}

private fun Item.increaseQuality(increaseBy: Int = 1) {
    if (quality + increaseBy > MAX_QUALITY) quality = MAX_QUALITY
    else quality += increaseBy
}

private fun Item.advanceByADay() {
    sellIn -= 1
}

private fun Item.decreaseQualityToZero() {
    quality = 0
}
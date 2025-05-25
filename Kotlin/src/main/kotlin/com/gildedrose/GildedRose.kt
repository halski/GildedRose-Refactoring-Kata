package com.gildedrose

const val BACKSTAGE_PASSES_ITEM_NAME = "Backstage passes to a TAFKAL80ETC concert"
const val SULFURAS_ITEM_NAME = "Sulfuras, Hand of Ragnaros"
const val BRIE_ITEM_NAME = "Aged Brie"
const val CONJURED_ITEM_NAME = "Conjured Mana Cake"

class GildedRose(private val items: List<Item>) {

    fun updateItems() {
        for (item in items) {
            item.update()
        }
    }
}

fun Item.update() {

    when (name) {
        BRIE_ITEM_NAME -> if (quality < 50) standardQualityIncrease()
        BACKSTAGE_PASSES_ITEM_NAME -> {
            if (quality < 50) {
                standardQualityIncrease()
                if (sellIn < 11) standardQualityIncrease()
                if (sellIn < 6) standardQualityIncrease()
            }
        }
        SULFURAS_ITEM_NAME -> {}
        else -> if (quality > 0) standardQualityDecrease()
    }

    when (name) {
        SULFURAS_ITEM_NAME -> {}
        else -> advanceByADay()
    }

    if (sellIn < 0) {
        when (name) {
            BRIE_ITEM_NAME -> if (quality < 50) standardQualityIncrease()
            BACKSTAGE_PASSES_ITEM_NAME -> quality = 0
            SULFURAS_ITEM_NAME -> {}
            else -> if (quality > 0) standardQualityDecrease()
        }
    }
}

private fun Item.standardQualityDecrease() {
    quality -= 1
}

private fun Item.standardQualityIncrease() {
    quality += 1
}

private fun Item.advanceByADay() {
    sellIn -= 1
}
package com.gildedrose

const val BACKSTAGE_PASSES_ITEM_NAME = "Backstage passes to a TAFKAL80ETC concert"
const val SULFURAS_ITEM_NAME = "Sulfuras, Hand of Ragnaros"
const val BRIE_ITEM_NAME = "Aged Brie"
const val CONJURED_ITEM_NAME = "Conjured Mana Cake"

class GildedRose(private val items: List<Item>) {

    fun updateQuality() {
        for (item in items) {
            item.update()
        }
    }
}

fun Item.update() {

    if (name != BRIE_ITEM_NAME && name != BACKSTAGE_PASSES_ITEM_NAME) {
        // Normal items, Sulfuras and Conjured items
        if (quality > 0) {
            if (name != SULFURAS_ITEM_NAME) {
                standardQualityDecrease()
            }
        }
    } else {
        // Aged Brie or Backstage passes
        if (quality < 50) {
            standardQualityIncrease()

            if (name == BACKSTAGE_PASSES_ITEM_NAME) {
                if (sellIn < 11) {
                    standardQualityIncrease()
                }

                if (sellIn < 6) {
                    standardQualityIncrease()
                }
            }
        }
    }

    // Handle sellIn date for items
    if (name != SULFURAS_ITEM_NAME) {
        advanceByADay()
    }

    if (sellIn < 0) {

        if (name != BRIE_ITEM_NAME) {
            if (name != BACKSTAGE_PASSES_ITEM_NAME) {
                // Normal items and Conjured items
                if (quality > 0) {
                    if (name != SULFURAS_ITEM_NAME) {
                        standardQualityDecrease()
                    }
                }
            } else {
                // Backstage passes
                quality = 0
            }
        } else {
            // Aged Brie
            if (quality < 50) {
                standardQualityIncrease()
            }
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
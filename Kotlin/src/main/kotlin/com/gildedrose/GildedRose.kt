package com.gildedrose

const val BACKSTAGE_PASSES_ITEM_NAME = "Backstage passes to a TAFKAL80ETC concert"
const val SULFURAS_ITEM_NAME = "Sulfuras, Hand of Ragnaros"
const val BRIE_ITEM_NAME = "Aged Brie"
const val CONJURED_ITEM_NAME = "Conjured Mana Cake"

class GildedRose(val items: List<Item>) {

    fun updateQuality() {

        for (i in items.indices) {
            if (items[i].name != BRIE_ITEM_NAME && items[i].name != BACKSTAGE_PASSES_ITEM_NAME) {
                if (items[i].quality > 0) {
                    if (items[i].name != SULFURAS_ITEM_NAME) {
                        items[i].quality = items[i].quality - 1
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1

                    if (items[i].name == BACKSTAGE_PASSES_ITEM_NAME) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1
                            }
                        }
                    }
                }
            }

            if (items[i].name != SULFURAS_ITEM_NAME) {
                items[i].sellIn = items[i].sellIn - 1
            }

            if (items[i].sellIn < 0) {
                if (items[i].name != BRIE_ITEM_NAME) {
                    if (items[i].name != BACKSTAGE_PASSES_ITEM_NAME) {
                        if (items[i].quality > 0) {
                            if (items[i].name != SULFURAS_ITEM_NAME) {
                                items[i].quality = items[i].quality - 1
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1
                    }
                }
            }
        }
    }

}
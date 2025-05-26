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
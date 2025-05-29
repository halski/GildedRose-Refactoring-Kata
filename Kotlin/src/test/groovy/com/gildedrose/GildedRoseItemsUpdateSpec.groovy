package com.gildedrose


import spock.lang.Specification

import static com.gildedrose.updater.ItemTypes.AGED_BRIE
import static com.gildedrose.updater.ItemTypes.BACKSTAGE_PASSES
import static com.gildedrose.updater.ItemTypes.CONJURED
import static com.gildedrose.updater.ItemTypes.SULFURAS

class GildedRoseItemsUpdateSpec extends Specification {

    private static final String STANDARD_ITEM = "Vanilla Item"

    private static final int POSITIVE_QUALITY = 10
    private static final int MAX_STANDARD_QUALITY = 50

    private static final int FUTURE_SELL_IN = 5
    private static final int SELL_IN_TODAY = 0
    private static final int SELL_IN_PASSED = -1

    private static final int BACKSTAGE_PASS_SELL_IN_ABOVE_THRESHOLDS = 11
    private static final int BACKSTAGE_PASS_SELL_IN_THRESHOLD_1 = 6
    private static final int BACKSTAGE_PASS_SELL_IN_THRESHOLD_2 = 5


    def "Should decrease quality accordingly to sellIn for standard items"() {
        given: "Standard item having defined sellIn"
            def items = [new Item(STANDARD_ITEM, initialSellIn, POSITIVE_QUALITY)]
        and: "App is configured with items"
            def app = new GildedRose(items)

        when: "Quality is updated"
            app.updateItems()

        then: "Item has quality decreased as expected"
            verifyAll(items[0]) {
                quality == POSITIVE_QUALITY - expectedQualityDecrease
                sellIn == initialSellIn - 1
            }

        where:
            initialSellIn  | expectedQualityDecrease
            FUTURE_SELL_IN | 1
            SELL_IN_TODAY  | 2
            SELL_IN_PASSED | 2
    }


    def "Should not decrease quality below 0 for degrading items"() {
        given: "App is configured with items"
            def app = new GildedRose(items)

        when: "Quality is updated"
            app.updateItems()

        then: "Quality is not decreased below 0"
            verifyAll(items[0]) {
                quality >= 0
                sellIn == SELL_IN_TODAY - 1
            }

        where: "Degrading items having 0 quality"
            items << [
                    [new Item(STANDARD_ITEM, SELL_IN_TODAY, 0)],
                    [new Item(BACKSTAGE_PASSES, SELL_IN_TODAY, 0)],
                    [new Item(CONJURED, SELL_IN_TODAY, 0)]
            ]
    }

    def "Should not increase quality above 50 for improving items"() {
        given: "App is configured with items"
            def app = new GildedRose(items)

        when: "Quality is updated"
            app.updateItems()

        then: "Quality is not increased above 50"
            verifyAll(items[0]) {
                quality == MAX_STANDARD_QUALITY
            }

        where: "Improving items having the coming quality update exceed maximum"
            items << [
                    [new Item(AGED_BRIE, FUTURE_SELL_IN, MAX_STANDARD_QUALITY)],
                    [new Item(AGED_BRIE, SELL_IN_PASSED, 49)],
                    [new Item(BACKSTAGE_PASSES, FUTURE_SELL_IN, MAX_STANDARD_QUALITY)],
                    [new Item(BACKSTAGE_PASSES, BACKSTAGE_PASS_SELL_IN_THRESHOLD_2, 49)],
            ]
    }

    def "Should not alter quality or sellIn for 'Sulfuras'"() {
        given: "'Sulfuras' item having defined sellIn"
            def items = [new Item(SULFURAS, initialSellIn, 80)]
        and: "App is configured with items"
            def app = new GildedRose(items)

        when: "Quality is updated"
            app.updateItems()

        then: "Item has quality and sellIn unchanged"
            verifyAll(items[0]) {
                sellIn == initialSellIn
                quality == 80
            }

        where: "SellIn can have any value"
            initialSellIn << [FUTURE_SELL_IN, SELL_IN_TODAY, SELL_IN_PASSED]
    }

    def "Should increase quality accordingly to sellIn for 'Aged Brie'"() {
        given: "'Aged Brie' item having defined sellIn"
            def items = [new Item(AGED_BRIE, initialSellIn, POSITIVE_QUALITY)]
        and: "App is configured with items"
            def app = new GildedRose(items)

        when: "Quality is updated"
            app.updateItems()

        then: "Item has quality increased as expected"
            verifyAll(items[0]) {
                quality == POSITIVE_QUALITY + expectedQualityIncrease
                sellIn == initialSellIn - 1
            }

        where:
            initialSellIn  | expectedQualityIncrease
            FUTURE_SELL_IN | 1
            SELL_IN_TODAY  | 2
            SELL_IN_PASSED | 2
    }

    def "Should modify quality accordingly to sellIn thresholds for 'Backstage Passes'"() {
        given: "'Backstage Passes' item having defined sellIn and quality"
            def items = [new Item(BACKSTAGE_PASSES, initialSellIn, POSITIVE_QUALITY)]
        and: "App is configured with items"
            def app = new GildedRose(items)

        when: "Quality is updated"
            app.updateItems()

        then: "Item has quality increased as expected"
            verifyAll(items[0]) {
                quality == expectedQuality
                sellIn == initialSellIn - 1
            }

        where:
            initialSellIn                           | expectedQuality
            BACKSTAGE_PASS_SELL_IN_ABOVE_THRESHOLDS | POSITIVE_QUALITY + 1
            BACKSTAGE_PASS_SELL_IN_THRESHOLD_1      | POSITIVE_QUALITY + 2
            BACKSTAGE_PASS_SELL_IN_THRESHOLD_2      | POSITIVE_QUALITY + 3
            SELL_IN_TODAY                           | 0
            SELL_IN_PASSED                          | 0
    }

    def "Should decrease quality accordingly to sellIn for 'Conjured' items"() {
        given: "'Conjured' item having defined sellIn"
            def items = [new Item(CONJURED, initialSellIn, POSITIVE_QUALITY)]
        and: "App is configured with items"
            def app = new GildedRose(items)

        when: "Quality is updated"
            app.updateItems()

        then: "Item has quality decreased as expected"
            verifyAll(items[0]) {
                quality == POSITIVE_QUALITY - expectedQualityDecrease
                sellIn == initialSellIn - 1
            }

        where:
            initialSellIn  | expectedQualityDecrease
            FUTURE_SELL_IN | 2
            SELL_IN_TODAY  | 4
            SELL_IN_PASSED | 4
    }

}
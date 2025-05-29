package com.gildedrose

import com.gildedrose.updater.BackstagePassQualityUpdater
import com.gildedrose.updater.ItemUpdaterProvider
import com.gildedrose.updater.NoOpUpdater
import com.gildedrose.updater.QualityDegradingByOneUpdater
import com.gildedrose.updater.QualityDegradingByTwoUpdater
import com.gildedrose.updater.QualityImprovingUpdater
import spock.lang.Specification

import static com.gildedrose.updater.ItemTypes.AGED_BRIE
import static com.gildedrose.updater.ItemTypes.BACKSTAGE_PASSES
import static com.gildedrose.updater.ItemTypes.CONJURED
import static com.gildedrose.updater.ItemTypes.SULFURAS

class ItemUpdaterProviderSpec extends Specification {
    def "Should return ItemUpdater for given item type"() {
        given: "Item of given type"
            def item = new Item(itemType, 10, 20)

        when : "ItemUpdater is requested"
            def itemUpdater = ItemUpdaterProvider.INSTANCE.findUpdaterFor(item)

        then: "ItemUpdater is not null"
            itemUpdater != null
        and: "ItemUpdater is of expected type"
            itemUpdater.class == expectedType

        where:
            itemType         | expectedType
            AGED_BRIE        | QualityImprovingUpdater
            SULFURAS         | NoOpUpdater
            BACKSTAGE_PASSES | BackstagePassQualityUpdater
            CONJURED         | QualityDegradingByTwoUpdater
            "OTHER"          | QualityDegradingByOneUpdater
    }
}
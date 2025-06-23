package com.gildedrose;

public class ItemUpdaterFactory {
    public static ItemUpdater getUpdater(Item item) {
        switch (item.name) {
            case "Aged Brie": return new AgedBrieUpdater();
            case "Backstage passes to a TAFKAL80ETC concert": return new BackstagePassUpdater();
            case "Sulfuras, Hand of Ragnaros": return new SulfurasUpdater();
            default: return new NormalItemUpdater();
        }
    }
}
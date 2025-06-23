package com.gildedrose;

public class GildedRose {
    private final Item[] items;
    private final Map<String, ItemUpdater> updaters = new HashMap<>();

    public GildedRose(Item[] items) {
        this.items = items;
        updaters.put("Aged Brie", new AgedBrieUpdater());
        updaters.put("Backstage passes to a TAFKAL80ETC concert", new BackstagePassUpdater());
        updaters.put("Sulfuras, Hand of Ragnaros", new SulfurasUpdater());
        updaters.put("Conjured Mana Cake", new ConjuredItemUpdater());
    }

    public void updateQuality() {
        for (Item item : items) {
            ItemUpdater updater = updaters.getOrDefault(item.name, new DefaultItemUpdater());
            updater.update(item);
        }
    }
}

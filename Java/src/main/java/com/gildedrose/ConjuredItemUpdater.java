public class ConjuredItemUpdater implements ItemUpdater {
    public void update(Item item) {
        item.sellIn--;
        item.quality = Math.max(0, item.quality - (item.sellIn < 0 ? 4 : 2));
    }
}
package me.darkolythe.deepstorageplus.dsu.managers;

import me.darkolythe.deepstorageplus.DeepStoragePlus;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class SorterUpdateManager {

    private DeepStoragePlus main;
    public SorterUpdateManager(DeepStoragePlus plugin) {
        main = plugin;
    }

    /*
    Update the items in the dsu. This is done when items are added, taken, Storage Containers are added, taken, and when opening the dsu.
     */
    public void sortItems(Inventory inv, Long minTimeSinceLast) {
        if (inv.getLocation() == null) {
            return;
        }
        if (!DeepStoragePlus.recentSortCalls.containsKey(inv.getLocation())) {
            DeepStoragePlus.recentSortCalls.put(inv.getLocation(), 0L);
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable() {
            @Override
            public void run() {
                // if over a second has passed since the last bulk item was placed in the dsu, update it
                if (!DeepStoragePlus.recentSortCalls.containsKey(inv.getLocation())) {
                    return;
                }
                if (System.currentTimeMillis() - DeepStoragePlus.recentSortCalls.get(inv.getLocation()) < minTimeSinceLast) {
                    return;
                }
                System.out.println("Move successful?" + SorterManager.sortItems(inv));
                DeepStoragePlus.recentSortCalls.put(inv.getLocation(), System.currentTimeMillis());
            }
        }, 5L);
    }
}

package me.darkolythe.deepstorageplus.dsu.managers;

import me.darkolythe.deepstorageplus.DeepStoragePlus;
import me.darkolythe.deepstorageplus.utils.LanguageManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class WirelessManager {

    public static ItemStack createTerminal() {
        ItemStack receiver = new ItemStack(Material.STONE_SHOVEL);
        ItemMeta meta = receiver.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.AQUA + LanguageManager.getValue("terminal"));
        meta.setLore(Arrays.asList(ChatColor.GRAY + "---------------------",
                                   ChatColor.RED.toString() + ChatColor.BOLD.toString() + LanguageManager.getValue("unlinked"),
                                   ChatColor.GRAY.toString() + LanguageManager.getValue("clicktolink"),
                                   ChatColor.GRAY + "---------------------"));
        receiver.setItemMeta(meta);
        receiver.setDurability((short)124);
        return receiver;
    }

    public static ItemStack createReceiver() {
        ItemStack receiver = new ItemStack(Material.STONE_SHOVEL);
        ItemMeta meta = receiver.getItemMeta();
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(ChatColor.AQUA + LanguageManager.getValue("receiver"));
        receiver.setItemMeta(meta);
        receiver.setDurability((short)112);
        return receiver;
    }

    public static void updateTerminal(ItemStack terminal, int x, int y, int z, World world) {
        ItemMeta meta = terminal.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(Arrays.asList(ChatColor.GREEN.toString() + ChatColor.BOLD.toString() + LanguageManager.getValue("linked"),
                                   ChatColor.GRAY.toString() + "X: " + x,
                                   ChatColor.GRAY.toString() + "Y: " + y,
                                   ChatColor.GRAY.toString() + "Z: " + z,
                                   ChatColor.GRAY.toString() + "World: " + world.getName(),
                                   "",
                                   ChatColor.GRAY + "Shift + Throw to Unlink"));
        terminal.setItemMeta(meta);
        terminal.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
    }

    public static Inventory getWirelessDSU(ItemStack terminal, Player player) {
        ItemMeta meta = terminal.getItemMeta();
        List<String> lore = meta.getLore();
        int x = Integer.parseInt(lore.get(1).replaceAll("^[^_]*:", "").replace(" ", ""));
        int y = Integer.parseInt(lore.get(2).replaceAll("^[^_]*:", "").replace(" ", ""));
        int z = Integer.parseInt(lore.get(3).replaceAll("^[^_]*:", "").replace(" ", ""));
        String world = ChatColor.GRAY.toString() + "World: " + player.getWorld().getName();
        if (world.equals(lore.get(4))) {
            Block block = player.getWorld().getBlockAt(x, y, z);
            if (block.getType() == Material.CHEST) {
                Chest chest = (Chest) block.getState();
                if (chest.getCustomName() != null && chest.getCustomName().equals(DeepStoragePlus.DSUname)) {
                    return chest.getInventory();
                } else {
                    player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + "Your DSU is no longer there");
                }
            } else {
                player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + "Your DSU is no longer there");
            }
        } else {
            player.sendMessage(DeepStoragePlus.prefix + ChatColor.RED + "You can't open that in " + ChatColor.GRAY + player.getWorld().getName());
        }
        return null;
    }
}
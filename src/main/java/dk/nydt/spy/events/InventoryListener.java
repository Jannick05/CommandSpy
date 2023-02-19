package dk.nydt.spy.events;

import dk.nydt.spy.SpyMain;
import dk.nydt.spy.utils.Chat;
import dk.nydt.spy.utils.Spytils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InventoryListener implements Listener {
    SpyMain plugin;

    public InventoryListener(SpyMain plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        String name = Chat.colored(SpyMain.configYML.getString("GUI.name"));
        Player p = (Player) e.getWhoClicked();

        if(e.getClickedInventory().getName().equals(name)) {
            e.setCancelled(true);
            if(e.getCurrentItem().getType() != Material.AIR) {
                if(e.getSlot() == 40) {
                    if(e.getCurrentItem().getType() == Material.INK_SACK) {
                        short data = e.getCurrentItem().getDurability();
                        if (data == 1) {
                            //DISABLE SPY
                            Spytils.removeSpy(p);
                        } else if (data == 10) {
                            //ENABLE SPY
                            Spytils.addSpy(p);
                        }
                        Inventory inv = e.getClickedInventory();
                        if (!Spytils.isSpy(p)) {
                            String enable_name = Chat.colored(SpyMain.configYML.getString("GUI.enable_name"));
                            List<String> enable_lore = SpyMain.configYML.getStringList("GUI.enable_lore");

                            ItemStack enable = new ItemStack(Material.INK_SACK, 1, (byte) 10);
                            ItemMeta enable_meta = enable.getItemMeta();
                            enable_meta.setDisplayName(enable_name);
                            ArrayList<String> enable_lores = new ArrayList<>();
                            for (String line : enable_lore) {
                                enable_lores.add(Chat.colored(line));
                            }
                            enable_meta.setLore(enable_lores);
                            enable.setItemMeta(enable_meta);
                            inv.setItem(40, enable);
                        } else {
                            String disable_name = Chat.colored(SpyMain.configYML.getString("GUI.disable_name"));
                            List<String> disable_lore = SpyMain.configYML.getStringList("GUI.disable_lore");

                            ItemStack disable = new ItemStack(Material.INK_SACK, 1, (byte) 1);
                            ItemMeta disable_meta = disable.getItemMeta();
                            disable_meta.setDisplayName(disable_name);
                            ArrayList<String> disable_lores = new ArrayList<>();
                            for (String line : disable_lore) {
                                disable_lores.add(Chat.colored(line));
                            }
                            disable_meta.setLore(disable_lores);
                            disable.setItemMeta(disable_meta);
                            inv.setItem(40, disable);
                        }
                    }
                } else if (e.getCurrentItem().getType() != Material.INK_SACK || e.getCurrentItem().getType() != Material.STAINED_GLASS_PANE) {
                    String playerName = e.getCurrentItem().getItemMeta().getDisplayName();
                    String color = SpyMain.configYML.getString("GUI.color");
                    String[] opponent_split = playerName.split(Chat.colored(color));
                    OfflinePlayer opponent_player = Bukkit.getOfflinePlayer(opponent_split[1]);

                    if (Spytils.isSpy(opponent_player)) {
                        Spytils.removeSpy(opponent_player);
                    }
                    e.getClickedInventory().clear(e.getSlot());
                }
            }
        }
    }
}

package dk.nydt.spy.commands;

import dk.nydt.spy.SpyMain;
import dk.nydt.spy.utils.Chat;
import dk.nydt.spy.utils.GUI;
import dk.nydt.spy.utils.GlassColor;
import dk.nydt.spy.utils.Spytils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Spy implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if (args.length == 0) {
            if (p.hasPermission(SpyMain.configYML.getString("Permissions.usage"))) {
                openSpyMenu(p, 0);
                return true;
            }
        } else {
            if (args[0].equalsIgnoreCase("reload")) {
                if (p.hasPermission(SpyMain.configYML.getString("Permissions.reload"))) {
                    SpyMain.config.reloadConfig();
                    SpyMain.configYML = SpyMain.config.getConfig();
                    SpyMain.data.reloadConfig();
                    SpyMain.dataYML = SpyMain.data.getConfig();
                    p.sendMessage(Chat.colored(SpyMain.configYML.getString("Messages.reload_success")));
                } else {
                    p.sendMessage(Chat.colored(SpyMain.configYML.getString("Messages.reload_failed")));
                }
            } else {
                p.sendMessage(Chat.colored(SpyMain.configYML.getString("Messages.argument_error")));
            }
        }
        return false;
    }

    public static void openSpyMenu(Player p, int page) {
        String admin_permission = SpyMain.configYML.getString("Permissions.admin");
        String inv_name = SpyMain.configYML.getString("GUI.name");
        String top_row = SpyMain.configYML.getString("GUI.top-row");
        String bottom_row = SpyMain.configYML.getString("GUI.bottom-row");
        String arrow_left = SpyMain.configYML.getString("GUI.arrow-left");
        String arrow_right = SpyMain.configYML.getString("GUI.arrow-right");
        String fail_head = SpyMain.configYML.getString("GUI.fail_head");
        String fail_name = SpyMain.configYML.getString("GUI.fail_name");
        List<String> fail_lore = SpyMain.configYML.getStringList("GUI.fail_lore");

        Inventory inv = Bukkit.createInventory(null, 9 * 5, Chat.colored(inv_name));
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, GUI.createItemGlass(Material.STAINED_GLASS_PANE, GlassColor.getGlassColor(top_row), "&7"));
        }

        for (int i = 36; i < 45; i++) {
            inv.setItem(i, GUI.createItemGlass(Material.STAINED_GLASS_PANE, GlassColor.getGlassColor(bottom_row), "&7"));
        }

        if (!p.hasPermission(admin_permission)) {
            ItemStack head = GUI.getSkull(fail_head);
            ItemMeta head_meta = head.getItemMeta();
            head_meta.setDisplayName(Chat.colored(fail_name));

            ArrayList<String> lore = new ArrayList<>();
            for (String line : fail_lore) {
                lore.add(Chat.colored(line));
            }
            head_meta.setLore(lore);
            head.setItemMeta(head_meta);
            inv.setItem(22, head);
        } else {
            UUID uuid = p.getUniqueId();
            ConfigurationSection spySection = SpyMain.dataYML.getConfigurationSection("Players");
            int size = spySection != null ? spySection.getKeys(true).size() : 0;
            int n = 0;
            int page_start = 45*page;
            int n2 = 1;
            String color = SpyMain.configYML.getString("GUI.color");
            List<String> disable_player_lore = SpyMain.configYML.getStringList("GUI.disable_player_lore");
            List<String> players = SpyMain.dataYML.getStringList("players");
            n = 9;
            for (String key : players) {
                UUID p_uuid = UUID.fromString(key);
                n2++;
                if (n2 >= page_start) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(p_uuid);
                    String playerName = player.getName();
                    ItemStack head = GUI.getPlayerSkull(playerName);
                    ItemMeta head_meta = head.getItemMeta();
                    head_meta.setDisplayName(Chat.colored(color + playerName));
                    List<String> player_lore = new ArrayList<>();
                    for (String line : disable_player_lore) {
                        line = line.replace("%player%", playerName);
                        player_lore.add(Chat.colored(line));
                    }
                    head_meta.setLore(player_lore);
                    head.setItemMeta(head_meta);
                    inv.setItem(n, head);
                    n++;

                    if (n >= 35) {
                        break;
                    }
                }
            }
        }
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
        p.openInventory(inv);
    }

}

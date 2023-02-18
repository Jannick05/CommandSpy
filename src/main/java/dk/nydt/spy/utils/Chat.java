package dk.nydt.spy.utils;

import org.bukkit.ChatColor;

public class Chat {
    public static String colored(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}

package dk.nydt.spy.utils;

import dk.nydt.spy.SpyMain;
import org.bukkit.OfflinePlayer;

import java.util.List;

public class Spytils {
    public static void addSpy(OfflinePlayer player) {
        List<String> current = SpyMain.dataYML.getStringList("players");
        current.add(player.getUniqueId().toString());
        SpyMain.dataYML.set("players", current);
        SpyMain.data.saveConfig();
    }
    public static void removeSpy(OfflinePlayer player) {
        List<String> current = SpyMain.dataYML.getStringList("players");
        current.remove(player.getUniqueId().toString());
        SpyMain.dataYML.set("players", current);
        SpyMain.data.saveConfig();
    }

    public static boolean isSpy(OfflinePlayer player) {
        List<String> players = SpyMain.dataYML.getStringList("players");
        return players.contains(player.getUniqueId().toString());
    }
}

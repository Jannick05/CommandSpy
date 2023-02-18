package dk.nydt.spy.utils;

import dk.nydt.spy.SpyMain;
import org.bukkit.entity.Player;

import java.util.List;

public class Spytils {
    public static void addSpy(Player player) {

    }
    public static void removeSpy(Player player) {

    }

    public static boolean isSpy(Player player) {
        List<String> players = SpyMain.dataYML.getStringList("Players");
        return players.contains(player.getUniqueId().toString());
    }
}

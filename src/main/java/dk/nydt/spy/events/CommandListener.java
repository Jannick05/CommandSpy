package dk.nydt.spy.events;

import dk.nydt.spy.SpyMain;
import dk.nydt.spy.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.UUID;

public class CommandListener implements Listener {

    SpyMain plugin;

    public CommandListener(SpyMain plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        String command = e.getMessage();
        String message = SpyMain.configYML.getString("Messages.spy");
        command = command.replace("/", "");
        message = message.replace("%player%", player.getName());
        message = message.replace("%command%", command);
        List<String> players = SpyMain.dataYML.getStringList("players");
        for (String pl : players) {
            Player p = Bukkit.getPlayer(UUID.fromString(pl));
            if (p.isOnline()) {
                p.sendMessage(Chat.colored(message));
            }
        }
    }
}

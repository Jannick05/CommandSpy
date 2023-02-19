package dk.nydt.spy;

import dk.nydt.spy.commands.Spy;
import dk.nydt.spy.events.CommandListener;
import dk.nydt.spy.events.InventoryListener;
import dk.nydt.spy.utils.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SpyMain extends JavaPlugin {
    public static SpyMain instance;
    private static PluginManager pluginManager;
    private boolean access = false;
    public static Config data, config, license;
    public static FileConfiguration dataYML, configYML, licenseYML;

    private String licenses;
    @Override
    public void onEnable() {
        pluginManager = getServer().getPluginManager();
        instance = this;

        //license yml
        if (!(new File(getDataFolder(), "license.yml")).exists())saveResource("license.yml", false);

        license = new Config(this, null, "license.yml");
        licenseYML = license.getConfig();
        System.out.println(licenseYML);
        licenses = licenseYML.getString("License");
        if(!new AdvancedLicense(licenses, "https://license.cutekat.dk/verify.php", this).debug().register()) return;
        access = true;

        //config yml
        if (!(new File(getDataFolder(), "config.yml")).exists())
            saveResource("config.yml", false);

        config = new Config(this, null, "config.yml");
        configYML = config.getConfig();

        //data yml
        if (!(new File(getDataFolder(), "data.yml")).exists())
            saveResource("data.yml", false);

        data = new Config(this, null, "data.yml");
        dataYML = data.getConfig();

        //register commands
        getCommand("Spy").setExecutor(new Spy());

        //register events
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new CommandListener(this), this);

    }

    @Override
    public void onDisable() {
        if (access) {
            config.saveConfig();
            data.saveConfig();
            license.saveConfig();
        }
        license.saveConfig();
    }
}

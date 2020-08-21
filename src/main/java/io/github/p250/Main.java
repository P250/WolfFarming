package io.github.p250;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.p250.commands.WolfFarmingReload;
import io.github.p250.events.BlockBreak;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class Main extends JavaPlugin {

    private RegionContainer regionManager;
    private FileConfiguration config;
    private File configFile;

    private void initConfig() {
        configFile = new File(getDataFolder(), "worldguard_region_settings.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        if (!(configFile.exists())) {
            HashMap<String, Object> defaults = new HashMap<String, Object>();

            defaults.put("worldguard.region.id", "potato");

            defaults.put("command.reload.success", "&aReloaded WolfFarming config.");
            defaults.put("command.reload.usage", "&c/wolffarming <reload>");
            defaults.put("command.reload.permission", "wolffarming.reload");

            defaults.put("plugin.replant.delay", 60);
            defaults.put("plugin.replant.permission", "wolffarming.replant");

            config.addDefaults(defaults);
            config.options().copyDefaults(true);
        }

        try {
            config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onEnable() {
        initConfig();
        regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer();

        getCommand("wolffarming").setExecutor(new WolfFarmingReload(this));
        Bukkit.getPluginManager().registerEvents(new BlockBreak(this), this);
    }

    public RegionContainer getRegionManager() {
        return regionManager;
    }

    @Override
    public FileConfiguration getConfig() {
        return config;
    }

    @Override
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        try {
            config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

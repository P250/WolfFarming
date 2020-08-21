package io.github.p250.events;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import io.github.p250.Main;
import io.github.p250.runnables.PotatoReplantRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitTask;

public class BlockBreak implements Listener {

    private Main plugin;
    private RegionContainer regionManager;

    public BlockBreak(Main instance) {
        this.plugin = instance;
        regionManager = plugin.getRegionManager();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!(e.getPlayer().hasPermission(plugin.getConfig().getString("plugin.replant.permission")))) {
            return;
        }
        if (e.getBlock().getType() == Material.POTATOES) {
            BlockVector3 blockLoc = BukkitAdapter.asBlockVector(e.getBlock().getLocation());

            World world = BukkitAdapter.adapt(e.getPlayer().getWorld());
            RegionManager regions = regionManager.get(world);

            ApplicableRegionSet set = regions.getApplicableRegions(blockLoc);
            if (set != null) {
                String regionID = plugin.getConfig().getString("worldguard.region.id");
                for (ProtectedRegion r : set.getRegions()) {
                    if (r.getId().equals(regionID)) {
                        int delay = plugin.getConfig().getInt("plugin.replant.delay");
                        new PotatoReplantRunnable(e.getBlock().getLocation()).runTaskLater(plugin, delay);
                    }
                }
            }

        }
    }


}

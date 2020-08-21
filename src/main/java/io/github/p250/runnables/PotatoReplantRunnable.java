package io.github.p250.runnables;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class PotatoReplantRunnable extends BukkitRunnable {

    private Location potatoLoc;
    private Location blockBelow;

    public PotatoReplantRunnable(Location loc) {
        potatoLoc = loc;
    }

    public void run() {
        blockBelow = potatoLoc.clone();
        blockBelow.setY(potatoLoc.getY() - 1);
        if (blockBelow.getBlock().getType() == Material.FARMLAND) {
            potatoLoc.getBlock().setType(Material.POTATOES);
            return;
        }
    }
}

package nandeyanen.click.snowball_fight;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Snowball_fight extends JavaPlugin implements Listener {

    private HashMap<Block, Integer> blockHitCounts = new HashMap<>();
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().name().contains("RIGHT")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && item.getType() == Material.SNOWBALL) {
                player.getInventory().addItem(new ItemStack(Material.SNOWBALL, 1));
            }
        }
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            Block hitBlock = event.getHitBlock();
            if (hitBlock != null && hitBlock.getType() != Material.BEDROCK) {
                int hitCount = blockHitCounts.getOrDefault(hitBlock, 0);
                hitCount++;
                blockHitCounts.put(hitBlock, hitCount);

                if (hitCount >= 5) {
                    if (hitBlock.getType() != Material.AIR) {
                        hitBlock.setType(Material.AIR);
                    }
                    blockHitCounts.remove(hitBlock);
                }
            }
            snowball.remove();
        }
    }
}

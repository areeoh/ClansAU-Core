package com.areeoh.core.recharge;

import com.areeoh.core.framework.Plugin;
import com.areeoh.core.framework.Manager;
import com.areeoh.core.framework.Module;
import com.areeoh.core.recharge.listeners.RechargeListener;
import com.areeoh.core.utility.UtilMessage;
import com.areeoh.core.utility.UtilTime;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RechargeManager extends Manager<Module> {

    private HashMap<UUID, Set<RechargeData>> rechargeHashMap = new HashMap<>();

    public RechargeManager(Plugin plugin) {
        super(plugin, "RechargeManager");
    }

    @Override
    public void registerModules() {
        addModule(new RechargeListener(this));
    }

    public boolean use(Player player, String ability, long recharge, boolean inform, boolean countdown) {
        if(recharge == 0L) {
            return true;
        }
        if (!rechargeHashMap.containsKey(player.getUniqueId())) {
            rechargeHashMap.put(player.getUniqueId(), new HashSet<>());
            rechargeHashMap.get(player.getUniqueId()).add(new RechargeData(ability, inform, countdown, recharge));
            return true;
        }
        if (rechargeHashMap.get(player.getUniqueId()).stream().noneMatch(rechargeData -> rechargeData.getName().equals(ability))) {
            rechargeHashMap.get(player.getUniqueId()).add(new RechargeData(ability, inform, countdown, recharge));
            return true;
        }
        if (rechargeHashMap.get(player.getUniqueId()).stream().filter(rechargeData -> rechargeData.getName().equals(ability)).anyMatch(rechargeData -> rechargeData.getRemaining() > 0)) {
            if (countdown) {
                UtilMessage.message(player, "Recharge", "You cannot use " + ChatColor.GREEN + ability + ChatColor.GRAY + " for " + ChatColor.GREEN + UtilTime.getTime(rechargeHashMap.get(player.getUniqueId()).stream().filter(rechargeData -> rechargeData.getName().equals(ability)).findFirst().get().getRemaining(), UtilTime.TimeUnit.BEST, 1) + ChatColor.GRAY + ".");
            }
            return false;
        }
        return rechargeHashMap.get(player.getUniqueId()).stream().filter(rechargeData -> rechargeData.getName().equals(ability)).anyMatch(rechargeData -> rechargeData.getRemaining() > 0);
    }

    public boolean isCooling(Player player, String ability, boolean inform) {
        if (!rechargeHashMap.containsKey(player.getUniqueId())) {
            return false;
        }
        if (rechargeHashMap.get(player.getUniqueId()).stream().noneMatch(rechargeData -> rechargeData.getName().equals(ability))) {
            return false;
        } else if (inform) {
            UtilMessage.message(player, "Recharge", "You cannot use " + ChatColor.GREEN + ability + ChatColor.GRAY + " for " + ChatColor.GREEN + UtilTime.getTime(rechargeHashMap.get(player.getUniqueId()).stream().filter(rechargeData -> rechargeData.getName().equals(ability)).findFirst().get().getRemaining(), UtilTime.TimeUnit.BEST, 1) + ChatColor.GRAY + ".");
        }
        return rechargeHashMap.get(player.getUniqueId()).stream().filter(rechargeData -> rechargeData.getName().equals(ability)).anyMatch(rechargeData -> rechargeData.getRemaining() > 0);
    }

    public void removeCooldown(Player player, String ability) {
        if (!rechargeHashMap.containsKey(player.getUniqueId())) {
            return;
        }
        if (rechargeHashMap.get(player.getUniqueId()).stream().noneMatch(rechargeData -> rechargeData.getName().equals(ability))) {
            return;
        }
        rechargeHashMap.get(player.getUniqueId()).remove(rechargeHashMap.get(player.getUniqueId()).stream().filter(rechargeData -> rechargeData.getName().equals(ability)).findFirst().get());
    }

    public HashMap<UUID, Set<RechargeData>> getRechargeHashMap() {
        return rechargeHashMap;
    }
}
package com.areeoh.recharge.listeners;

import com.areeoh.framework.Module;
import com.areeoh.framework.updater.Update;
import com.areeoh.framework.updater.Updater;
import com.areeoh.recharge.RechargeData;
import com.areeoh.recharge.RechargeManager;
import com.areeoh.utility.UtilMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class RechargeListener extends Module<RechargeManager> implements Listener, Updater {

    public RechargeListener(RechargeManager manager) {
        super(manager, "RechargeListener");
    }

    @Update(ticks = 1)
    public void onUpdate() {
        Iterator<Map.Entry<UUID, Set<RechargeData>>> iterator = getManager(RechargeManager.class).getRechargeHashMap().entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<UUID, Set<RechargeData>> rechargeData = iterator.next();
            if (rechargeData.getValue().stream().anyMatch(rechargeData1 -> rechargeData1.getRemaining() <= 0)) {
                if (rechargeData.getValue().stream().filter(rechargeData1 -> rechargeData1.getRemaining() <= 0).findFirst().get().isInform()) {
                    if (Bukkit.getPlayer(rechargeData.getKey()) != null) {
                        UtilMessage.message(Bukkit.getPlayer(rechargeData.getKey()), "Recharge", "You can now use " + ChatColor.GREEN + rechargeData.getValue().stream().filter(rechargeData1 -> rechargeData1.getRemaining() <= 0).findFirst().get().getName() + ChatColor.GRAY + ".");
                    }
                }
                rechargeData.getValue().stream().filter(rechargeData1 -> rechargeData1.getRemaining() <= 0).findFirst().ifPresent(rData -> rechargeData.getValue().remove(rData));
                if (rechargeData.getValue().isEmpty()) {
                    iterator.remove();
                }
            }
        }
    }


}
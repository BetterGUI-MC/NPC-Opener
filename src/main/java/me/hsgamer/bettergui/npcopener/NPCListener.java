package me.hsgamer.bettergui.npcopener;

import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.Optional;

public class NPCListener implements Listener {

    @EventHandler
    public void onLeftClick(NPCLeftClickEvent event) {
        Player player = event.getClicker();
        int id = event.getNPC().getId();
        Optional<Map.Entry<InteractiveNPC, String>> optional = Main.getStorage().getLeftMenu(id);
        if (optional.isPresent()) {
            Map.Entry<InteractiveNPC, String> entry = optional.get();
            String menu = entry.getValue();
            if (BetterGUI.getInstance().getMenuManager().contains(menu)) {
                event.setCancelled(true);
                BetterGUI.getInstance().getMenuManager().openMenu(menu, player, entry.getKey().getArgs(), false);
            } else {
                MessageUtils.sendMessage(player, MessageConfig.MENU_NOT_FOUND.getValue());
            }
        }
    }

    @EventHandler
    public void onRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        int id = event.getNPC().getId();
        Optional<Map.Entry<InteractiveNPC, String>> optional = Main.getStorage().getRightMenu(id);
        if (optional.isPresent()) {
            Map.Entry<InteractiveNPC, String> entry = optional.get();
            String menu = entry.getValue();
            if (BetterGUI.getInstance().getMenuManager().contains(menu)) {
                event.setCancelled(true);
                BetterGUI.getInstance().getMenuManager().openMenu(menu, player, entry.getKey().getArgs(), false);
            } else {
                MessageUtils.sendMessage(player, MessageConfig.MENU_NOT_FOUND.getValue());
            }
        }
    }
}

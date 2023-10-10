package me.hsgamer.bettergui.npcopener;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Map;
import java.util.Optional;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public class NPCListener implements Listener {
    private final Main main;

    public NPCListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onLeftClick(NPCLeftClickEvent event) {
        Player player = event.getClicker();
        int id = event.getNPC().getId();
        Optional<Map.Entry<InteractiveNPC, String>> optional = main.getStorage().getLeftMenu(id);
        if (optional.isPresent()) {
            Map.Entry<InteractiveNPC, String> entry = optional.get();
            String menu = entry.getValue();
            if (getInstance().getMenuManager().contains(menu)) {
                event.setCancelled(true);
                getInstance().getMenuManager().openMenu(menu, player, entry.getKey().getArgs(), false);
            } else {
                MessageUtils.sendMessage(player, getInstance().getMessageConfig().menuNotFound);
            }
        }
    }

    @EventHandler
    public void onRightClick(NPCRightClickEvent event) {
        Player player = event.getClicker();
        int id = event.getNPC().getId();
        Optional<Map.Entry<InteractiveNPC, String>> optional = main.getStorage().getRightMenu(id);
        if (optional.isPresent()) {
            Map.Entry<InteractiveNPC, String> entry = optional.get();
            String menu = entry.getValue();
            if (getInstance().getMenuManager().contains(menu)) {
                event.setCancelled(true);
                getInstance().getMenuManager().openMenu(menu, player, entry.getKey().getArgs(), false);
            } else {
                MessageUtils.sendMessage(player, getInstance().getMessageConfig().menuNotFound);
            }
        }
    }

    @EventHandler
    public void onOperatorJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) return;
        MessageUtils.sendMessage(player, "&cYou are seeing this message because the addon is deprecated & planned to be removed.", "&e[NPC-Opener@BetterGUI] ");
        MessageUtils.sendMessage(player, "&cPlease use Citizens NPC Commands instead.", "&e[NPC-Opener@BetterGUI] ");
        MessageUtils.sendMessage(player, "&bTo convert the data, please use &e/convertnpcmenu", "&e[NPC-Opener@BetterGUI] ");
    }
}

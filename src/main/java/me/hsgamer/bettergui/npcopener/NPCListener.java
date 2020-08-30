package me.hsgamer.bettergui.npcopener;

import java.util.Map;
import java.util.Optional;
import me.hsgamer.bettergui.BetterGUI;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.util.MessageUtils;
import net.citizensnpcs.api.event.NPCClickEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListener implements Listener {

  @EventHandler
  public void onLeftClick(NPCClickEvent event) {
    Player player = event.getClicker();
    int id = event.getNPC().getId();
    Optional<Map.Entry<InteractiveNPC, String>> optional = event instanceof NPCLeftClickEvent ?
        Main.getStorage().getLeftMenu(id) : Main.getStorage().getRightMenu(id);
    if (optional.isPresent()) {
      Map.Entry<InteractiveNPC, String> entry = optional.get();
      String menu = entry.getValue();
      if (BetterGUI.getInstance().getMenuManager().contains(menu)) {
        event.setCancelled(true);
        BetterGUI.getInstance().getMenuManager()
            .openMenu(menu, player, entry.getKey().getArgs(), false);
      } else {
        MessageUtils.sendMessage(player, MessageConfig.MENU_NOT_FOUND.getValue());
      }
    }
  }
}

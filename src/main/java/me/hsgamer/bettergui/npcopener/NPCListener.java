package me.hsgamer.bettergui.npcopener;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

import java.util.Map;
import java.util.Optional;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.util.CommonUtils;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NPCListener implements Listener {

  @EventHandler
  public void onLeftClick(NPCLeftClickEvent event) {
    Player player = event.getClicker();
    int id = event.getNPC().getId();
    Optional<Map.Entry<InteractiveNPC, String>> optional = Main.getStorage().getLeftMenu(id);
    if (optional.isPresent()) {
      Map.Entry<InteractiveNPC, String> entry = optional.get();
      String menu = entry.getValue();
      if (getInstance().getMenuManager().contains(menu)) {
        event.setCancelled(true);
        getInstance().getMenuManager()
            .openMenu(menu, player, entry.getKey().getArgs(), false);
      } else {
        CommonUtils.sendMessage(player,
            getInstance().getMessageConfig().get(DefaultMessage.MENU_NOT_FOUND));
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
      if (getInstance().getMenuManager().contains(menu)) {
        event.setCancelled(true);
        getInstance().getMenuManager()
            .openMenu(menu, player, entry.getKey().getArgs(), false);
      } else {
        CommonUtils.sendMessage(player,
            getInstance().getMessageConfig().get(DefaultMessage.MENU_NOT_FOUND));
      }
    }
  }
}

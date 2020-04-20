package me.hsgamer.bettergui.npcopener.command;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.bettergui.npcopener.Main.getStorage;
import static me.hsgamer.bettergui.util.CommonUtils.sendMessage;

import java.util.Arrays;
import java.util.Collections;
import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.npcopener.InteractiveNPC;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Set extends BukkitCommand {

  private static final Permission PERMISSION = Permissions
      .createPermission("bettergui.setnpcmenu", null, PermissionDefault.OP);

  public Set() {
    super("setnpcmenu", "Bind a menu to an NPC",
        "/setnpcmenu <menu> [leftClick] [rightClick] [args]",
        Collections.singletonList("snm"));
  }

  @Override
  public boolean execute(CommandSender sender, String s, String[] args) {
    if (!(sender instanceof Player)) {
      sendMessage(sender,
          getInstance().getMessageConfig().get(DefaultMessage.PLAYER_ONLY));
      return false;
    }
    if (!sender.hasPermission(PERMISSION)) {
      sendMessage(sender,
          getInstance().getMessageConfig().get(DefaultMessage.NO_PERMISSION));
      return false;
    }

    if (args.length <= 0) {
      sendMessage(sender,
          getInstance().getMessageConfig().get(DefaultMessage.MENU_REQUIRED));
      return false;
    }

    NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
    if (npc != null) {
      InteractiveNPC interactiveNPC = new InteractiveNPC(npc.getId());
      if (args.length >= 4) {
        interactiveNPC
            .setArgs(Arrays.asList(Arrays.copyOfRange(args, 3, args.length)));
      }
      if (args.length >= 3) {
        getStorage()
            .set(interactiveNPC, args[0], Boolean.parseBoolean(args[1]),
                Boolean.parseBoolean(args[2]));
      } else {
        getStorage().set(interactiveNPC, args[0]);
      }
      sendMessage(sender, getInstance().getMessageConfig()
          .get(DefaultMessage.SUCCESS));
    } else {
      sendMessage(sender, getInstance().getMessageConfig()
          .get(String.class, "npc-required", "&cYou need to select an NPC"));
      return false;
    }
    return true;
  }
}

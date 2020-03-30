package me.hsgamer.bettergui.npcopener.command;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.bettergui.npcopener.Main.getStorage;
import static me.hsgamer.bettergui.util.CommonUtils.sendMessage;

import java.util.Collections;
import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.util.TestCase;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Remove extends BukkitCommand {

  private static final Permission PERMISSION = Permissions
      .createPermission("bettergui.removenpcmenu", null, PermissionDefault.OP);

  public Remove() {
    super("removenpcmenu", "Remove the binding to the menu",
        "/removenpcmenu [leftClick] [rightClick]", Collections
            .singletonList("rnm"));
  }

  @Override
  public boolean execute(CommandSender sender, String s, String[] args) {
    return TestCase.create(sender)
        .setPredicate(commandSender -> commandSender instanceof Player)
        .setFailConsumer(commandSender ->
            sendMessage(commandSender,
                getInstance().getMessageConfig().get(DefaultMessage.PLAYER_ONLY)))
        .setSuccessNextTestCase(
            new TestCase<CommandSender>()
                .setPredicate(commandSender -> commandSender.hasPermission(PERMISSION))
                .setFailConsumer(commandSender -> sendMessage(commandSender,
                    getInstance().getMessageConfig().get(DefaultMessage.NO_PERMISSION)))
                .setSuccessConsumer(commandSender -> {
                  NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(commandSender);
                  if (npc != null) {
                    int id = npc.getId();
                    if (args.length >= 2) {
                      getStorage().remove(id, Boolean.parseBoolean(args[0]),
                          Boolean.parseBoolean(args[1]));
                    } else {
                      getStorage().remove(id);
                    }
                    sendMessage(commandSender, getInstance().getMessageConfig()
                        .get(DefaultMessage.SUCCESS));
                  } else {
                    sendMessage(commandSender, getInstance().getMessageConfig()
                        .get(String.class, "npc-required", "&cYou need to select an NPC"));
                  }
                })
        )
        .test();
  }
}

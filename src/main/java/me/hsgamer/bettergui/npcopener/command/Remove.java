package me.hsgamer.bettergui.npcopener.command;

import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.lib.core.bukkit.utils.PermissionUtils;
import me.hsgamer.bettergui.npcopener.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Collections;

import static me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils.sendMessage;
import static me.hsgamer.bettergui.npcopener.Main.getStorage;

public class Remove extends BukkitCommand {

    private static final Permission PERMISSION = PermissionUtils
            .createPermission("bettergui.removenpcmenu", null, PermissionDefault.OP);

    public Remove() {
        super("removenpcmenu", "Remove the binding to the menu",
                "/removenpcmenu [leftClick] [rightClick]", Collections
                        .singletonList("rnm"));
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, MessageConfig.PLAYER_ONLY.getValue());
            return false;
        }
        if (!sender.hasPermission(PERMISSION)) {
            sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
            return false;
        }

        NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
        if (npc != null) {
            int id = npc.getId();
            if (args.length >= 2) {
                getStorage().remove(id, Boolean.parseBoolean(args[0]),
                        Boolean.parseBoolean(args[1]));
            } else {
                getStorage().remove(id);
            }
            sendMessage(sender, MessageConfig.SUCCESS.getValue());
        } else {
            sendMessage(sender, Main.NPC_REQUIRED.getValue());
            return false;
        }
        return true;
    }
}

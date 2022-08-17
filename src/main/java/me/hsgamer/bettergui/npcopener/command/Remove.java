package me.hsgamer.bettergui.npcopener.command;

import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.npcopener.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Collections;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

public class Remove extends BukkitCommand {

    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".removenpcmenu", PermissionDefault.OP);
    private final Main main;

    public Remove(Main main) {
        super("removenpcmenu", "Remove the binding to the menu",
                "/removenpcmenu [leftClick] [rightClick]", Collections
                        .singletonList("rnm"));
        this.main = main;
        setPermission(PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!testPermission(sender)) {
            return false;
        }
        if (!(sender instanceof Player)) {
            sendMessage(sender, getInstance().getMessageConfig().playerOnly);
            return false;
        }

        NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
        if (npc == null) {
            sendMessage(sender, main.getMessageConfig().npcRequired);
            return false;
        }
        int id = npc.getId();
        if (args.length >= 2) {
            main.getStorage().remove(id, Boolean.parseBoolean(args[0]),
                    Boolean.parseBoolean(args[1]));
        } else {
            main.getStorage().remove(id);
        }
        sendMessage(sender, getInstance().getMessageConfig().success);
        return true;
    }
}

package me.hsgamer.bettergui.npcopener.command;

import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.npcopener.InteractiveNPC;
import me.hsgamer.bettergui.npcopener.Main;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Arrays;
import java.util.Collections;

import static me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils.sendMessage;
import static me.hsgamer.bettergui.npcopener.Main.getStorage;

public class Set extends BukkitCommand {

    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".setnpcmenu", PermissionDefault.OP);

    public Set() {
        super("setnpcmenu", "Bind a menu to an NPC",
                "/setnpcmenu <menu> [leftClick] [rightClick] [args]",
                Collections.singletonList("snm"));
        setPermission(PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        if (!testPermission(sender)) {
            return false;
        }
        if (!(sender instanceof Player)) {
            sendMessage(sender, MessageConfig.PLAYER_ONLY.getValue());
            return false;
        }

        if (args.length <= 0) {
            sendMessage(sender, MessageConfig.MENU_REQUIRED.getValue());
            return false;
        }

        NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
        if (npc == null) {
            sendMessage(sender, Main.NPC_REQUIRED.getValue());
            return false;
        }
        if (getStorage().contains(npc.getId())) {
            sendMessage(sender, Main.NPC_ALREADY_SET.getValue());
            return false;
        }

        InteractiveNPC interactiveNPC = new InteractiveNPC(npc.getId());
        if (args.length >= 4) {
            interactiveNPC.setArgs(Arrays.copyOfRange(args, 3, args.length));
        }
        if (args.length >= 3) {
            getStorage()
                    .set(interactiveNPC, args[0], Boolean.parseBoolean(args[1]),
                            Boolean.parseBoolean(args[2]));
        } else {
            getStorage().set(interactiveNPC, args[0]);
        }
        sendMessage(sender, MessageConfig.SUCCESS.getValue());

        return true;
    }
}

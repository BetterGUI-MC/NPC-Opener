package me.hsgamer.bettergui.npcopener.command;

import me.hsgamer.bettergui.Permissions;
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

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

public class Set extends BukkitCommand {

    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".setnpcmenu", PermissionDefault.OP);
    private final Main main;

    public Set(Main main) {
        super("setnpcmenu", "Bind a menu to an NPC",
                "/setnpcmenu <menu> [leftClick] [rightClick] [args]",
                Collections.singletonList("snm"));
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

        if (args.length <= 0) {
            sendMessage(sender, getInstance().getMessageConfig().menuRequired);
            return false;
        }

        NPC npc = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
        if (npc == null) {
            sendMessage(sender, main.getMessageConfig().npcRequired);
            return false;
        }
        if (main.getStorage().contains(npc.getId())) {
            sendMessage(sender, main.getMessageConfig().npcAlreadySet);
            return false;
        }

        InteractiveNPC interactiveNPC = new InteractiveNPC(npc.getId());
        if (args.length >= 4) {
            interactiveNPC.setArgs(Arrays.copyOfRange(args, 3, args.length));
        }
        if (args.length >= 3) {
            main.getStorage()
                    .set(interactiveNPC, args[0], Boolean.parseBoolean(args[1]),
                            Boolean.parseBoolean(args[2]));
        } else {
            main.getStorage().set(interactiveNPC, args[0]);
        }
        sendMessage(sender, getInstance().getMessageConfig().success);

        return true;
    }
}

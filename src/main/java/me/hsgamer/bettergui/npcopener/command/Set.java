package me.hsgamer.bettergui.npcopener.command;

import me.hsgamer.bettergui.Permissions;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Collections;

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
        sender.sendMessage("§cThis command is deprecated, please use Citizens' command instead");
        return true;
    }
}

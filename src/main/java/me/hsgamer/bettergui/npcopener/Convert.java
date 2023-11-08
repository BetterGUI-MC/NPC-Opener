package me.hsgamer.bettergui.npcopener;

import me.hsgamer.bettergui.Permissions;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.CommandTrait;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Collections;
import java.util.Map;

public class Convert extends Command {
    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".convertnpcmenu", PermissionDefault.OP);
    private final Main main;

    public Convert(Main main) {
        super("convertnpcmenu", "Convert current settings to Citizens' settings", "/convertnpcmenu", Collections.singletonList("cnm"));
        this.main = main;
        setPermission(PERMISSION.getName());
    }

    private void convert(Map<InteractiveNPC, String> map, CommandTrait.Hand hand) {
        for (Map.Entry<InteractiveNPC, String> entry : map.entrySet()) {
            InteractiveNPC interactiveNPC = entry.getKey();
            String menu = entry.getValue();
            String[] args = interactiveNPC.getArgs();
            int id = interactiveNPC.getId();

            String openCommand = "openmenu " + menu + " <p> " + String.join(" ", args);

            NPC npc = CitizensAPI.getNPCRegistry().getById(id);
            if (npc == null) {
                continue;
            }

            CommandTrait trait = npc.getOrAddTrait(CommandTrait.class);
            trait.addCommand(new CommandTrait.NPCCommandBuilder(openCommand, hand));
        }
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (!testPermission(sender)) {
            return false;
        }
        convert(main.getStorage().getNpcToLeftMenuMap(), CommandTrait.Hand.LEFT);
        convert(main.getStorage().getNpcToRightMenuMap(), CommandTrait.Hand.RIGHT);
        sender.sendMessage("Converted. You can remove this addon.");
        return false;
    }
}

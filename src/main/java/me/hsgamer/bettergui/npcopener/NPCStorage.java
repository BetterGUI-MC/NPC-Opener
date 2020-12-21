package me.hsgamer.bettergui.npcopener;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import me.hsgamer.bettergui.lib.simpleyaml.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NPCStorage {

    private final Map<InteractiveNPC, String> npcToLeftMenuMap = new HashMap<>();
    private final Map<InteractiveNPC, String> npcToRightMenuMap = new HashMap<>();
    private final BetterGUIAddon addon;

    public NPCStorage(BetterGUIAddon addon) {
        this.addon = addon;
        load();
    }

    public void load() {
        npcToLeftMenuMap.clear();
        npcToRightMenuMap.clear();
        if (addon.getConfig().isSet("left")) {
            ConfigurationSection section = addon.getConfig().getConfigurationSection("left");
            section.getKeys(false).forEach(s -> section.getConfigurationSection(s).getValues(false).forEach((s1, o) -> {
                InteractiveNPC npc = new InteractiveNPC(Integer.parseInt(s1));
                npc.setArgs(String.valueOf(o).split(" "));
                npcToLeftMenuMap.put(npc, s + ".yml");
            }));
        }
        if (addon.getConfig().isSet("right")) {
            ConfigurationSection section = addon.getConfig().getConfigurationSection("right");
            section.getKeys(false).forEach(s -> section.getConfigurationSection(s).getValues(false).forEach((s1, o) -> {
                InteractiveNPC npc = new InteractiveNPC(Integer.parseInt(s1));
                npc.setArgs(String.valueOf(o).split(" "));
                npcToRightMenuMap.put(npc, s + ".yml");
            }));
        }
    }

    public void save() {
        addon.getConfig().getKeys(false).forEach(s -> addon.getConfig().set(s, null));
        npcToLeftMenuMap.forEach((npc, s) -> {
            s = "left." + s.replace(".yml", "") + "." + npc.getId();
            addon.getConfig().set(s, String.join(" ", npc.getArgs()));
        });
        npcToRightMenuMap.forEach((npc, s) -> {
            s = "right." + s.replace(".yml", "") + "." + npc.getId();
            addon.getConfig().set(s, String.join(" ", npc.getArgs()));
        });
        addon.saveConfig();
    }

    public void set(InteractiveNPC npc, String menu, boolean leftClick, boolean rightClick) {
        if (leftClick) {
            npcToLeftMenuMap.put(npc, menu);
        }
        if (rightClick) {
            npcToRightMenuMap.put(npc, menu);
        }
    }

    public void set(InteractiveNPC npc, String menu) {
        set(npc, menu, true, true);
    }

    public void remove(int id, boolean leftClick, boolean rightClick) {
        if (leftClick) {
            npcToLeftMenuMap.entrySet().removeIf(entry -> entry.getKey().getId() == id);
        }
        if (rightClick) {
            npcToRightMenuMap.entrySet().removeIf(entry -> entry.getKey().getId() == id);
        }
    }

    public void remove(int id) {
        remove(id, true, true);
    }

    public boolean contains(int id) {
        return getLeftMenu(id).isPresent() || getRightMenu(id).isPresent();
    }

    public Optional<Map.Entry<InteractiveNPC, String>> getLeftMenu(int id) {
        return npcToLeftMenuMap.entrySet().stream().filter(entry -> entry.getKey().getId() == id).findFirst();
    }

    public Optional<Map.Entry<InteractiveNPC, String>> getRightMenu(int id) {
        return npcToRightMenuMap.entrySet().stream().filter(entry -> entry.getKey().getId() == id).findFirst();
    }
}

package me.hsgamer.bettergui.npcopener;

import me.hsgamer.hscore.config.Config;

import java.util.*;

public class NPCStorage {

    private final Map<InteractiveNPC, String> npcToLeftMenuMap = new HashMap<>();
    private final Map<InteractiveNPC, String> npcToRightMenuMap = new HashMap<>();
    private final Main main;

    public NPCStorage(Main main) {
        this.main = main;
    }

    @SuppressWarnings("unchecked")
    public void load() {
        npcToLeftMenuMap.clear();
        npcToRightMenuMap.clear();
        Config config = main.getConfig();
        config.getNormalizedValues("left", false).forEach((k, v) -> {
            if (!(v instanceof List)) {
                return;
            }
            ((List<?>) v).forEach(o -> {
                if (o instanceof Map) {
                    npcToLeftMenuMap.put(InteractiveNPC.deserialize((Map<String, Object>) o), k + ".yml");
                }
            });
        });
        config.getNormalizedValues("right", false).forEach((k, v) -> {
            if (!(v instanceof List)) {
                return;
            }
            ((List<?>) v).forEach(o -> {
                if (o instanceof Map) {
                    npcToRightMenuMap.put(InteractiveNPC.deserialize((Map<String, Object>) o), k + ".yml");
                }
            });
        });
    }

    public void save() {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        npcToLeftMenuMap.forEach((npc, s) -> {
            s = "left." + s.replace(".yml", "");
            map.computeIfAbsent(s, s1 -> new ArrayList<>()).add(npc.serialize());
        });
        npcToRightMenuMap.forEach((npc, s) -> {
            s = "right." + s.replace(".yml", "");
            map.computeIfAbsent(s, s1 -> new ArrayList<>()).add(npc.serialize());
        });

        // Clear old config
        main.getConfig().getKeys(false).forEach(main.getConfig()::remove);

        map.forEach((s, list) -> main.getConfig().set(s, list));
        main.getConfig().save();
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
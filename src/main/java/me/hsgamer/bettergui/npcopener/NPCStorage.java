package me.hsgamer.bettergui.npcopener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.configuration.ConfigurationSection;

public class NPCStorage {

  private final Map<InteractiveNPC, String> npcToLeftMenuMap = new HashMap<>();
  private final Map<InteractiveNPC, String> npcToRightMenuMap = new HashMap<>();
  private final Addon addon;

  public NPCStorage(Addon addon) {
    this.addon = addon;
    load();
  }

  @SuppressWarnings("unchecked")
  public void load() {
    if (addon.getConfig().isSet("left")) {
      ConfigurationSection section = addon.getConfig().getConfigurationSection("left");
      section.getKeys(false).forEach(
          s -> addon.getConfig().getMapList(s)
              .forEach(map -> npcToLeftMenuMap.put(InteractiveNPC.deserialize(
                  (Map<String, Object>) map), s + ".yml")));
    }
    if (addon.getConfig().isSet("right")) {
      ConfigurationSection section = addon.getConfig().getConfigurationSection("right");
      section.getKeys(false).forEach(
          s -> addon.getConfig().getMapList(s)
              .forEach(map -> npcToRightMenuMap.put(InteractiveNPC.deserialize(
                  (Map<String, Object>) map), s + ".yml")));
    }
  }

  public void save() {
    Map<String, List<Map<String, Object>>> map = new HashMap<>();
    npcToLeftMenuMap.forEach((npc, s) -> {
      s = "left." + s.replace(".yml", "");
      map.putIfAbsent(s, new ArrayList<>());
      map.get(s).add(npc.serialize());
    });
    npcToRightMenuMap.forEach((npc, s) -> {
      s = "right." + s.replace(".yml", "");
      map.putIfAbsent(s, new ArrayList<>());
      map.get(s).add(npc.serialize());
    });
    map.forEach((s, list) -> addon.getConfig().set(s, list));
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
    save();
  }

  public void remove(int id) {
    remove(id, true, true);
  }

  public Optional<Map.Entry<InteractiveNPC, String>> getLeftMenu(int id) {
    return npcToLeftMenuMap.entrySet().stream().filter(entry -> entry.getKey().getId() == id)
        .findFirst();
  }

  public Optional<Map.Entry<InteractiveNPC, String>> getRightMenu(int id) {
    return npcToRightMenuMap.entrySet().stream().filter(entry -> entry.getKey().getId() == id)
        .findFirst();
  }
}

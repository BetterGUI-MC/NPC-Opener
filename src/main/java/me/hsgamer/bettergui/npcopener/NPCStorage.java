package me.hsgamer.bettergui.npcopener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class NPCStorage {

  private Map<Integer, String> idToLeftMenuMap = new HashMap<>();
  private Map<Integer, String> idToRightMenuMap = new HashMap<>();
  private Addon addon;
  private FileConfiguration config;

  public NPCStorage(Addon addon) {
    this.addon = addon;
    this.config = addon.getConfig();
    load();
  }

  public void load() {
    if (config.isSet("left")) {
      ConfigurationSection section = config.getConfigurationSection("left");
      section.getKeys(false).forEach(
          s -> config.getIntegerList(s).forEach(id -> idToLeftMenuMap.put(id, s + ".yml")));
    }
    if (config.isSet("right")) {
      ConfigurationSection section = config.getConfigurationSection("right");
      section.getKeys(false).forEach(
          s -> config.getIntegerList(s).forEach(id -> idToRightMenuMap.put(id, s + ".yml")));
    }
  }

  public void save() {
    Map<String, List<Integer>> map = new HashMap<>();
    idToLeftMenuMap.forEach((id, s) -> {
      s = "left." + s.replace(".yml", "");
      map.putIfAbsent(s, new ArrayList<>());
      map.get(s).add(id);
    });
    idToRightMenuMap.forEach((id, s) -> {
      s = "right." + s.replace(".yml", "");
      map.putIfAbsent(s, new ArrayList<>());
      map.get(s).add(id);
    });
    map.forEach(config::set);
    addon.saveConfig();
  }

  public void set(int id, String menu, boolean leftClick, boolean rightClick) {
    if (leftClick) {
      idToLeftMenuMap.put(id, menu);
    }
    if (rightClick) {
      idToRightMenuMap.put(id, menu);
    }
  }

  public void set(int id, String menu) {
    set(id, menu, true, true);
  }

  public void remove(int id, boolean leftClick, boolean rightClick) {
    if (leftClick) {
      idToLeftMenuMap.remove(id);
      config.set("left." + id, null);
    }
    if (rightClick) {
      idToRightMenuMap.remove(id);
      config.set("right." + id, null);
    }
    addon.saveConfig();
  }

  public void remove(int id) {
    remove(id, true, true);
  }

  public Optional<String> getLeftMenu(int id) {
    return Optional.ofNullable(idToLeftMenuMap.get(id));
  }

  public Optional<String> getRightMenu(int id) {
    return Optional.ofNullable(idToRightMenuMap.get(id));
  }
}

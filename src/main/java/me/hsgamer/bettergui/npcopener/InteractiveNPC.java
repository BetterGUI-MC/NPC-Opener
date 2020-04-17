package me.hsgamer.bettergui.npcopener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class InteractiveNPC implements ConfigurationSerializable {

  private final int id;
  private final List<String> args = new ArrayList<>();

  public InteractiveNPC(int id) {
    this.id = id;
  }

  @SuppressWarnings("unchecked")
  public static InteractiveNPC deserialize(Map<String, Object> map) {
    InteractiveNPC npc = new InteractiveNPC((Integer) map.get("id"));
    if (map.containsKey("args")) {
      npc.setArgs((List<String>) map.get("args"));
    }
    return npc;
  }

  public int getId() {
    return id;
  }

  public List<String> getArgs() {
    return args;
  }

  public void setArgs(List<String> strings) {
    args.addAll(strings);
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", id);
    if (!args.isEmpty()) {
      map.put("args", args);
    }
    return map;
  }
}

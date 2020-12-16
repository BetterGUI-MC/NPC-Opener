package me.hsgamer.bettergui.npcopener;

import me.hsgamer.bettergui.lib.simpleyaml.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class InteractiveNPC implements ConfigurationSerializable {

    private final int id;
    private String[] args = new String[0];

    public InteractiveNPC(int id) {
        this.id = id;
    }

    public static InteractiveNPC deserialize(Map<String, Object> map) {
        InteractiveNPC npc = new InteractiveNPC((Integer) map.get("id"));
        if (map.containsKey("args")) {
            npc.setArgs(((String) map.get("args")).split(" "));
        }
        return npc;
    }

    public int getId() {
        return id;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        if (args.length > 0) {
            map.put("args", String.join(" ", args));
        }
        return map;
    }
}

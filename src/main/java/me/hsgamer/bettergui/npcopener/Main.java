package me.hsgamer.bettergui.npcopener;

import me.hsgamer.bettergui.config.ConfigPath;
import me.hsgamer.bettergui.npcopener.command.Remove;
import me.hsgamer.bettergui.npcopener.command.Set;
import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public final class Main extends Addon {

  public static final ConfigPath<String> NPC_REQUIRED = new ConfigPath<>(String.class,
      "npc-required", "&cYou need to select an NPC");
  public static final ConfigPath<String> NPC_ALREADY_SET = new ConfigPath<>(String.class,
      "npc-already-set", "&cThe NPC is already set");
  private static NPCStorage storage;

  public static NPCStorage getStorage() {
    return storage;
  }

  @Override
  public boolean onLoad() {
    ConfigurationSerialization.registerClass(InteractiveNPC.class);
    setupConfig();
    registerListener(new NPCListener());
    NPC_REQUIRED.setConfig(getPlugin().getMessageConfig());
    NPC_ALREADY_SET.setConfig(getPlugin().getMessageConfig());
    getPlugin().getMessageConfig().saveConfig();
    return true;
  }

  @Override
  public void onEnable() {
    storage = new NPCStorage(this);
    registerCommand(new Set());
    registerCommand(new Remove());
  }

  @Override
  public void onDisable() {
    storage.save();
  }

  @Override
  public void onReload() {
    storage.save();
    reloadConfig();
    storage.load();
  }
}

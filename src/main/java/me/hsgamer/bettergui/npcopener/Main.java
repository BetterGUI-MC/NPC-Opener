package me.hsgamer.bettergui.npcopener;

import me.hsgamer.bettergui.npcopener.command.Remove;
import me.hsgamer.bettergui.npcopener.command.Set;
import me.hsgamer.hscore.bukkit.addon.PluginAddon;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.Config;
import org.bukkit.event.HandlerList;

import java.io.File;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public final class Main extends PluginAddon {

    private final ExtraMessageConfig messageConfig = new ExtraMessageConfig(new BukkitConfig(new File(getDataFolder(), "messages.yml")));
    private final Config config = new BukkitConfig(new File(getDataFolder(), "config.yml"));
    private final NPCStorage storage = new NPCStorage(this);
    private final Set set = new Set(this);
    private final Remove remove = new Remove(this);
    private final NPCListener listener = new NPCListener(this);

    @Override
    public void onEnable() {
        messageConfig.setup();
        config.setup();
        storage.load();
        getInstance().registerListener(listener);
        getInstance().registerCommand(set);
        getInstance().registerCommand(remove);
    }

    @Override
    public void onDisable() {
        storage.save();
        HandlerList.unregisterAll(listener);
        getInstance().getCommandManager().unregister(set);
        getInstance().getCommandManager().unregister(remove);
    }

    @Override
    public void onReload() {
        storage.save();
        config.reload();
        messageConfig.reload();
        storage.load();
    }

    public Config getConfig() {
        return config;
    }

    public ExtraMessageConfig getMessageConfig() {
        return messageConfig;
    }

    public NPCStorage getStorage() {
        return storage;
    }
}

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
    private final Config config = new BukkitConfig(new File(getDataFolder(), "config.yml"));
    private final NPCStorage storage = new NPCStorage(this);
    private final Set set = new Set();
    private final Remove remove = new Remove();
    private final NPCListener listener = new NPCListener(this);

    @Override
    public void onEnable() {
        config.setup();
        storage.load();
        getInstance().registerListener(listener);
        getInstance().registerCommand(set);
        getInstance().registerCommand(remove);
    }

    @Override
    public void onPostEnable() {
        getLogger().warning("This addon is deprecated. Please use Citizens NPC Commands instead.");
        getLogger().warning("To convert the data, please use /convertnpcmenu");
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
        storage.load();
    }

    public Config getConfig() {
        return config;
    }

    public NPCStorage getStorage() {
        return storage;
    }
}

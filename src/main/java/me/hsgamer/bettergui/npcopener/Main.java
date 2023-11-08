package me.hsgamer.bettergui.npcopener;

import me.hsgamer.bettergui.api.addon.GetPlugin;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.expansion.common.Expansion;
import me.hsgamer.hscore.expansion.extra.expansion.DataFolder;
import me.hsgamer.hscore.logger.common.LogLevel;
import me.hsgamer.hscore.logger.provider.LoggerProvider;

import java.io.File;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public final class Main implements Expansion, GetPlugin, DataFolder {
    private final Config config = new BukkitConfig(new File(getDataFolder(), "config.yml"));
    private final NPCStorage storage = new NPCStorage(this);
    private final Convert convert = new Convert(this);

    @Override
    public void onEnable() {
        config.setup();
        storage.load();
        getInstance().registerCommand(convert);

        LoggerProvider.getLogger("NPC-Opener").log(LogLevel.WARN, "This addon is deprecated. Please use Citizens NPC Commands instead.");
        LoggerProvider.getLogger("NPC-Opener").log(LogLevel.WARN, "To convert the data, please use /convertnpcmenu");
    }

    @Override
    public void onDisable() {
        storage.save();
        getPlugin().getCommandManager().unregister(convert);
    }

    public Config getConfig() {
        return config;
    }

    public NPCStorage getStorage() {
        return storage;
    }
}

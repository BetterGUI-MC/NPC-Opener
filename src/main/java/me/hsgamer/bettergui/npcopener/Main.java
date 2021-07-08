package me.hsgamer.bettergui.npcopener;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import me.hsgamer.bettergui.lib.core.config.path.StringConfigPath;
import me.hsgamer.bettergui.npcopener.command.Remove;
import me.hsgamer.bettergui.npcopener.command.Set;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public final class Main extends BetterGUIAddon {

    public static final StringConfigPath NPC_REQUIRED = new StringConfigPath("npc-required", "&cYou need to select an NPC");
    public static final StringConfigPath NPC_ALREADY_SET = new StringConfigPath("npc-already-set", "&cThe NPC is already set");
    private static NPCStorage storage;

    public static NPCStorage getStorage() {
        return storage;
    }

    @Override
    public boolean onLoad() {
        setupConfig();
        registerListener(new NPCListener());
        NPC_REQUIRED.setConfig(getInstance().getMessageConfig());
        NPC_ALREADY_SET.setConfig(getInstance().getMessageConfig());
        getInstance().getMessageConfig().save();
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

package me.hsgamer.bettergui.npcopener;

import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.config.annotated.AnnotatedConfig;
import me.hsgamer.hscore.config.annotation.ConfigPath;

public class ExtraMessageConfig extends AnnotatedConfig {
    public final @ConfigPath("npc-required") String npcRequired;
    public final @ConfigPath("npc-already-set") String npcAlreadySet;

    public ExtraMessageConfig(Config config) {
        super(config);
        npcRequired = "&cYou need to select an NPC";
        npcAlreadySet = "&cThe NPC is already set";
    }
}

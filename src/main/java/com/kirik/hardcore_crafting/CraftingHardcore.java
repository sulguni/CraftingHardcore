package com.kirik.hardcore_crafting;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Info.MOD_ID, version = Info.VERSION, acceptableRemoteVersions = "*")
public class CraftingHardcore {
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        if (config.hasChanged()) {
            config.save();
        }
    }
}

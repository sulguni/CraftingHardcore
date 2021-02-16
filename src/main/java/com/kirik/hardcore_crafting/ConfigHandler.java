package com.kirik.hardcore_crafting;
import net.minecraftforge.common.config.Config;

@Config(modid = Info.MOD_ID)
public class ConfigHandler {
    @Config.RangeDouble(min = 0.0, max = 1.0)
    public static double chance = 0.3;
}
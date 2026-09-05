package net.mcexpanded.fancytabsections;

import net.minecraftforge.common.ForgeConfigSpec;

public class FTSConfig
{
    private static final ForgeConfigSpec.Builder BUILDER_CLIENT = new ForgeConfigSpec.Builder();

    //minigame & overlays positioning
    public static final ForgeConfigSpec.BooleanValue INDEX_EXPANDED = BUILDER_CLIENT
            .translation("fancytabsections.configuration.index_expanded")
            .define("index_expanded", true);

    static final ForgeConfigSpec SPEC = BUILDER_CLIENT.build();
}

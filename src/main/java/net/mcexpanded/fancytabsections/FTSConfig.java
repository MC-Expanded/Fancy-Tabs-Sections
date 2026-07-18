package net.mcexpanded.fancytabsections;

import net.neoforged.neoforge.common.ModConfigSpec;

public class FTSConfig
{
    private static final ModConfigSpec.Builder BUILDER_CLIENT = new ModConfigSpec.Builder();

    //minigame & overlays positioning
    public static final ModConfigSpec.BooleanValue INDEX_EXPANDED = BUILDER_CLIENT
            .translation("fancytabsections.configuration.index_expanded")
            .define("index_expanded", true);

    static final ModConfigSpec SPEC = BUILDER_CLIENT.build();
}

package net.mcexpanded.fancytabsections.creativetab;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoadingException;
import net.neoforged.fml.ModLoadingIssue;

/**
 * @since 2.0
 * @deprecated use {@link net.mcexpanded.fancytabsections.Section.SectionTextured}
 */
public record SectionTextured() implements Section
{
    static
    {
        if (true)
            throw new ModLoadingException(
                    ModLoadingIssue.error(
                            "A mod is using v2.0 of FancyTabSections, even though the currently loaded version is v3"
                            + " - Please let the mod author know they need to update FTS to the newest version as v3 contains API breaking changes."
                    ));
    }

    public static SectionTextured of(ResourceLocation doNotCall, Component doNotCall2, int doNotCall3, ConglomerateOfItems doNotCall4)
    {
        throw new ModLoadingException(
                ModLoadingIssue.error(
                        "A mod is using v2.0 of FancyTabSections, even though the currently loaded version is v3"
                        + " - Please let the mod author know they need to update FTS to the newest version as v3 contains API breaking changes."
                ));
    }
}



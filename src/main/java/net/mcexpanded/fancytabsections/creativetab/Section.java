package net.mcexpanded.fancytabsections.creativetab;

import net.neoforged.fml.ModLoadingException;
import net.neoforged.fml.ModLoadingIssue;

import java.util.List;

/**
 * @since 2.0
 * @deprecated use {@link net.mcexpanded.fancytabsections.Section.Section}
 */
public interface Section
{
    Object THROW_ON_LOAD = throwOnLoad();

    static Object throwOnLoad()
    {
        throw new ModLoadingException(
                ModLoadingIssue.error(
                        "A mod is using v2.0 of FancyTabSections, even though the currently loaded version is v3"
                        + " - Please let the mod author know they need to update FTS to the newest version as v3 contains API breaking changes."
                ));
    }
}
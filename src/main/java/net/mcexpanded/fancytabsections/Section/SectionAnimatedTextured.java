package net.mcexpanded.fancytabsections.Section;

import net.mcexpanded.fancytabsections.creativetab.ConglomerateOfItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/**
 * @since 3.0
 */
public class SectionAnimatedTextured extends SectionTextured
{
    int frames = 0;
    float frameTimeInMS = 200;

    public SectionAnimatedTextured(Identifier id)
    {
        super(id);
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, Font font, int topLeftX, int topLeftY)
    {
        long currentTimeMs = System.currentTimeMillis();
        int currentFrame = (int) ((currentTimeMs / (long)frameTimeInMS) % frames);

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                texture, topLeftX, topLeftY,
                0, currentFrame * verticalSize,
                horizontalSize, verticalSize,
                horizontalSize, verticalSize,
                horizontalSize, verticalSize * frames
        );

        this.renderTitle(guiGraphics, font, topLeftX, topLeftY);
    }

    /**
     * The texture must be placed at [namespace]:textures/gui/fancy_tab_section/[path].png when using this builder
     * @since 3.0
     */
    public static SectionAnimatedTextured of(Identifier id, Component title, int frames, float frameTimeInMS, int textColor, boolean textShadow, boolean collapsible, ConglomerateOfItems items)
    {
        SectionAnimatedTextured sat = new SectionAnimatedTextured(id);

        sat.setTitle(title);
        sat.setTextColor(textColor);
        sat.setTextShadow(textShadow);
        sat.setCollapsible(collapsible);
        sat.setItems(items);

        sat.setFrames(frames);
        sat.setFrameTimeInMS(frameTimeInMS);

        return sat;
    }

    public SectionAnimatedTextured setFrames(int frames)
    {
        this.frames = frames;
        return this;
    }

    public SectionAnimatedTextured setFrameTimeInMS(float frameTimeInMS)
    {
        this.frameTimeInMS = frameTimeInMS;
        return this;
    }
}
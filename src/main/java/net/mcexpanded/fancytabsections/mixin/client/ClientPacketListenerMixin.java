package net.mcexpanded.fancytabsections.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateRecipesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateTagsPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public abstract class ClientPacketListenerMixin
{
    @Unique
    private boolean fts$tagsHandled;
    @Unique
    private ClientboundUpdateRecipesPacket fts$pendingRecipes;

    @Inject(method = "handleUpdateRecipes", at = @At("HEAD"), cancellable = true)
    private void fts$deferRecipes(ClientboundUpdateRecipesPacket packet, CallbackInfo ci)
    {
        if (!Minecraft.getInstance().isSameThread()) return;
        if (!fts$tagsHandled)
        {
            fts$pendingRecipes = packet;
            ci.cancel();
        }
    }

    @Inject(method = "handleUpdateTags", at = @At("TAIL"))
    private void fts$afterTags(ClientboundUpdateTagsPacket packet, CallbackInfo ci)
    {
        fts$tagsHandled = true;
        ClientboundUpdateRecipesPacket pending = fts$pendingRecipes;
        if (pending != null)
        {
            fts$pendingRecipes = null;
            ((ClientPacketListener) (Object) this).handleUpdateRecipes(pending);
        }
    }
}
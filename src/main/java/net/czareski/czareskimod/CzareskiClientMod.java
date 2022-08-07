package net.czareski.czareskimod;

import net.czareski.czareskimod.block.ModBlocks;
import net.czareski.czareskimod.client.model.CannonballModel;
import net.czareski.czareskimod.client.renderer.CannonballRenderer;
import net.czareski.czareskimod.entity.CannonballEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CzareskiClientMod implements ClientModInitializer {
    public static final EntityModelLayer MODEL_CANNNONBALL_LAYER = new EntityModelLayer(new Identifier("czareskimod", "cannonball"), "main");
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RADAR, RenderLayer.getCutout());

        EntityRendererRegistry.register(CzareskiMod.CANNONBALL, CannonballRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(MODEL_CANNNONBALL_LAYER, CannonballModel::getTexturedModelData);
    }
}
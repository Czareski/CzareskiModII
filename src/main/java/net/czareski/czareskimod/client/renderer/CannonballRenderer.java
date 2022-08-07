package net.czareski.czareskimod.client.renderer;

import net.czareski.czareskimod.CzareskiClientMod;
import net.czareski.czareskimod.block.custom.Cannon;
import net.czareski.czareskimod.client.model.CannonballModel;
import net.czareski.czareskimod.entity.CannonballEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SlimeOverlayFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class CannonballRenderer extends MobEntityRenderer<CannonballEntity, CannonballModel> {

    public CannonballRenderer(EntityRendererFactory.Context context) {
        super(context, new CannonballModel(context.getPart(CzareskiClientMod.MODEL_CANNNONBALL_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(CannonballEntity entity) {
        return new Identifier("czareskimod", "textures/block/cobblestone.png");
    }
}

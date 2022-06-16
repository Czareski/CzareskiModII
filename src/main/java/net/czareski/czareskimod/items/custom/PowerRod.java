package net.czareski.czareskimod.items.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;


public class PowerRod extends Item {

    public PowerRod(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if (context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            player.sendMessage(new LiteralText("Position of clicked block is: " + String.valueOf(positionClicked.getX() + " " + String.valueOf(positionClicked.getY()) + " " + String.valueOf(positionClicked.getZ()))), false);
            for (int i = 0; i < 10; i++) {
                context.getWorld().addParticle(ParticleTypes.SOUL, (double) positionClicked.getX() + 0.5D, (double) positionClicked.getY() + 1D, (double) positionClicked.getZ() + 0.5D, 0D, 0.2D, 0D);
            }
            EndermanEntity enderman = new EndermanEntity(EntityType.ENDERMAN, context.getWorld());
            enderman.setPosition(positionClicked.getX() + 0.5D, positionClicked.getY() + 1D, positionClicked.getZ() + 0.5D);
            context.getWorld().spawnEntity(enderman);
            }
        return super.useOnBlock(context);
    }

}

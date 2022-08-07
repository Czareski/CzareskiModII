package net.czareski.czareskimod.items.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;


public class PowerRod extends Item {

    public PowerRod(Settings settings) {
        super(settings);
    }

    Random rand = new Random();
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        if (context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            player.sendMessage(new LiteralText("Position of clicked block is: " + String.valueOf(positionClicked.getX() + " " + String.valueOf(positionClicked.getY()) + " " + String.valueOf(positionClicked.getZ()))), false);
            for (int i = 0; i < 10; i++) {
                context.getWorld().addParticle(ParticleTypes.SOUL, (double) positionClicked.getX() + 0.5D, (double) positionClicked.getY() + 1D, (double) positionClicked.getZ() + 0.5D, rand.nextDouble(0.2D-(-0.2D)) + (-0.2D), rand.nextDouble(1D-0.2D) + 0.2D, rand.nextDouble(0.2D-(-0.2D)) + (-0.2D));
            }
//            ??EndermanEntity enderman = new EndermanEntity(EntityType.ENDERMAN, context.getWorld());
//            ??EndermanEntity enderman = ((EntityType<EndermanEntity>) EntityType.get("minecraft:enderman").get()).create(context.getWorld());
//            ??enderman.updatePosition(positionClicked.getX() + 0.5D, positionClicked.getY() + 1D, positionClicked.getZ() + 0.5D);
//            ??context.getWorld().spawnEntity(enderman);


        }
        return super.useOnBlock(context);
    }
}

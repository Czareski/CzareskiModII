package net.czareski.czareskimod.block.custom;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Trapblock extends Block {
    public Trapblock(Settings settings) { super(settings); }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient()) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = ((LivingEntity) entity);
                if (!livingEntity.isInvisible()) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 50, 50));
                }
                if (!livingEntity.isPlayer()) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 50, 50));
                }
            }
        }


        super.onSteppedOn(world, pos, state, entity);
    }
}

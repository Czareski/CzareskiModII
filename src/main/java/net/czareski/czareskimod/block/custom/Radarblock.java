package net.czareski.czareskimod.block.custom;

import com.ibm.icu.text.TimeZoneFormat;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.EntityList;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.List;

public class Radarblock extends Block {
    public Radarblock(Settings settings) {
        super(settings);
    }
    @Override

    public ActionResult onUse(BlockState blockstate, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        Box box = new Box(pos).expand(10);
        if (world.isClient()) {
            if (hand == Hand.MAIN_HAND) {
                player.sendMessage(new LiteralText("MAIN"), true);
                List entityList = world.getEntitiesByClass(MobEntity.class, box, EntityPredicates.VALID_ENTITY);
                if (entityList.isEmpty()) {
                    player.sendMessage(new LiteralText("Entities not found"), false);
                } else {
                    player.sendMessage(new LiteralText("Entities found:"), false);
                    for (int i = 0; i < entityList.size(); i++) {
                        LivingEntity entity = (LivingEntity)entityList.get(i);
                        player.sendMessage(new LiteralText((i + 1) + ": " + entity.getDisplayName()), false);
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 50, 50));
                        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 50, 50));
                        createParticleOnLine(world, pos, entity.getBlockPos(), player);
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    public ActionResult createParticleOnLine(World world, BlockPos startPos, BlockPos entityPos, PlayerEntity player) {


            double xdif = entityPos.getX() - startPos.getX();
            double ydif = entityPos.getY() - startPos.getY();
            double zdif = entityPos.getZ() - startPos.getZ();

            int particleNumConstant = 10; //number of particles
            double x = 0;
            double y = 0;
            double z = 0;
            while (Math.abs(x) < Math.abs(xdif)) {
                world.addParticle(ParticleTypes.GLOW, startPos.getX() + x + 0.5, startPos.getY() + y  + 0.5, startPos.getZ() + z  + 0.5, 0, 1, 0);
                x = x + xdif / particleNumConstant;
                y = y + ydif / particleNumConstant;
                z = z + zdif / particleNumConstant;
            }

            return ActionResult.SUCCESS;
    }
    int lessOrMoreThanZero(int var) {
        if (var == 0) {
            return 0;
        } else {
            return (var < 0) ? -1 : 1;
        }
    }
}

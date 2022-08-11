package net.czareski.czareskimod.entity;

import com.google.common.collect.Sets;
import net.czareski.czareskimod.CzareskiMod;
import net.czareski.czareskimod.items.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potions;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class CannonballEntity extends PersistentProjectileEntity {
    private World world;
    private int explosionPower = 1;

    public CannonballEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.world = world;
    }


    public CannonballEntity(double x, double y, double z, World world) {
        super(CzareskiMod.CANNONBALL, world);
        this.setPosition(x, y, z);
        this.world = world;
    }


    @Override
    protected ItemStack asItemStack() {
        return ModItems.CANNONBALL.getDefaultStack();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        return;
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        if (!this.world.isClient) {
            if (!state.isAir()) {
                boolean bl = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
                this.world.createExplosion((Entity) null, this.getX(), this.getY(), this.getZ(), (float) this.explosionPower, bl, bl ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
                this.discard();
            }
        }
    }
}

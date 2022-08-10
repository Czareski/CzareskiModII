package net.czareski.czareskimod.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CannonballEntity extends ExplosiveProjectileEntity {

    public CannonballEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);

    }

    @Override
    protected void initDataTracker() {
        
    }
}

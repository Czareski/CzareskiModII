package net.czareski.czareskimod.entity;

import com.google.common.collect.Sets;
import net.czareski.czareskimod.CzareskiMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
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
import net.minecraft.world.World;

public class CannonballEntity extends PersistentProjectileEntity {

    public CannonballEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }


    public CannonballEntity(EntityType<? extends PersistentProjectileEntity> entityType, double x, double y, double z, World world) {
        super(entityType, world);
        this.setPosition(x, y, z);
    }


    @Override
    protected ItemStack asItemStack() {
        return null;
    }

}

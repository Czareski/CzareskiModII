package net.czareski.czareskimod;

import net.czareski.czareskimod.block.ModBlocks;
import net.czareski.czareskimod.entity.CannonballEntity;
import net.czareski.czareskimod.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CzareskiMod implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "czareskimod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final EntityType<CannonballEntity> CANNONBALL = Registry.register(
			Registry.ENTITY_TYPE,
			new Identifier("czareskimod", "cannonball"),
			FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CannonballEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).build()
	);


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.RegisterModItems();
		ModBlocks.RegisterModBlocks();
		FabricDefaultAttributeRegistry.register(CANNONBALL, CannonballEntity.createMobAttributes());
	}
}

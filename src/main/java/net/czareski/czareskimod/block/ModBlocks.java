package net.czareski.czareskimod.block;

import net.czareski.czareskimod.CzareskiMod;
import net.czareski.czareskimod.block.custom.Cannon;
import net.czareski.czareskimod.block.custom.Radarblock;
import net.czareski.czareskimod.block.custom.Trapblock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block POWER_ORE = registerBlock("power_ore", new Block(FabricBlockSettings.of(Material.STONE).strength(3f).requiresTool()), ItemGroup.MISC);
    public static final Block TRAP_BLOCK = registerBlock("trap_block", new Trapblock(FabricBlockSettings.of(Material.STONE).strength(3f).requiresTool()), ItemGroup.MISC);
    public static final Block RADAR = registerBlock("radar_block", new Radarblock(FabricBlockSettings.of(Material.METAL).luminance(5).strength(3f).requiresTool()), ItemGroup.MISC);
    public static final Block CANNON = registerBlock("cannon", new Cannon(FabricBlockSettings.of(Material.METAL).strength(3f).requiresTool()), ItemGroup.MISC);


    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(CzareskiMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(CzareskiMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void RegisterModBlocks() {
        CzareskiMod.LOGGER.info("Registering mod block for" + CzareskiMod.MOD_ID);
    }
}

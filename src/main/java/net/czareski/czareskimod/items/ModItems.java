package net.czareski.czareskimod.items;

import net.czareski.czareskimod.CzareskiMod;
import net.czareski.czareskimod.items.custom.PowerRod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class ModItems {

    private static final Item STICK_OF_POWER = registerItem("stick_of_power",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    private static final Item POWER_DUST = registerItem("power_dust",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    private static final Item POWER_ROD = registerItem("power_rod",
            new PowerRod(new FabricItemSettings().group(ItemGroup.MISC)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(CzareskiMod.MOD_ID, name), item);
    }
    public static void RegisterModItems() {
        CzareskiMod.LOGGER.info("Registering mod item for" + CzareskiMod.MOD_ID);
    }
}

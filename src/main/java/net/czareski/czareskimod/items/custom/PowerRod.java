package net.czareski.czareskimod.items.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
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
        }
        return super.useOnBlock(context);
    }

}

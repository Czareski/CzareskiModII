package net.czareski.czareskimod.block.custom;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.czareski.czareskimod.CzareskiMod;
import net.czareski.czareskimod.entity.CannonballEntity;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Property;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Map;

public class Cannon extends Block {
    public static final DirectionProperty FACING;
    private static final Map<Item, DispenserBehavior> BEHAVIORS;


    public Cannon(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));

    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING});
    }

    @Override

    public ActionResult onUse(BlockState blockstate, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
                if (hand == Hand.MAIN_HAND) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    Item item = player.getStackInHand(hand).getItem();
                    switch (item.getTranslationKey()) {
                        case ("item.czareskimod.cannonball"):
                            strzel(serverWorld, pos, player.getStackInHand(hand), player);
                            break;
                    }
            }
        }
        return ActionResult.SUCCESS;
    }


    public static Position getOutputLocation(BlockPointer pointer) {
        Direction direction = (Direction)pointer.getBlockState().get(FACING);
        double d = pointer.getX() + 0.7D * (double)direction.getOffsetX();
        double e = pointer.getY() + 0.7D * (double)direction.getOffsetY();
        double f = pointer.getZ() + 0.7D * (double)direction.getOffsetZ();
        return new PositionImpl(d, e, f);
    }

    public void strzel(ServerWorld world, BlockPos pos, ItemStack itemStack, PlayerEntity player) {
        BlockPointerImpl blockPointerImpl = new BlockPointerImpl(world, pos);
        Position position = this.getOutputLocation(blockPointerImpl);
        Direction direction = (Direction)blockPointerImpl.getBlockState().get(FACING);
        ProjectileEntity cannonball = strzala(world, position);
        cannonball.setVelocity((double)direction.getOffsetX(), (double)((float)direction.getOffsetY() + 0.1F), (double)direction.getOffsetZ(),  1.5F,2.0F);
        world.spawnEntity(cannonball);
        itemStack.decrement(1);
    }

    public ProjectileEntity strzala(World world, Position position) {
        CannonballEntity cannonball = new CannonballEntity(position.getX(), position.getY(), position.getZ(), world);
        cannonball.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return cannonball;
    }


    static {
        FACING = HorizontalFacingBlock.FACING;
        BEHAVIORS = (Map)Util.make(new Object2ObjectOpenHashMap(), (map) -> {
            map.defaultReturnValue(new ItemDispenserBehavior());
        });
    }
}

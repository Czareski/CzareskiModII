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
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;

import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Map;
import java.util.stream.Stream;

public class Cannon extends Block {
    public static final DirectionProperty FACING;
    private static final Map<Item, DispenserBehavior> BEHAVIORS;


    public Cannon(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));

    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        // Intellij zaproponował użycie jakiegoś nowoczesnego switcha więc skorzystałem, ale pierwszy raz taki widzę. Szczurek
        return switch (state.get(FACING)) {
            case EAST -> SHAPE_E;
            case SOUTH -> SHAPE_S;
            case WEST -> SHAPE_W;
            default -> SHAPE_N;
        };
    }

    @Override
    public ActionResult onUse(BlockState blockstate, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
                if (hand == Hand.MAIN_HAND) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    Item item = player.getStackInHand(hand).getItem();
                    switch (item.getTranslationKey()) {
                        case ("item.minecraft.arrow"):
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

    private static final VoxelShape SHAPE_N = Stream.of(
            Block.createCuboidShape(13, 0, 2, 16, 12, 5),
            Block.createCuboidShape(0, 0, 11, 3, 12, 14),
            Block.createCuboidShape(0, 0, 2, 3, 12, 5),
            Block.createCuboidShape(13, 0, 11, 16, 12, 14),
            Block.createCuboidShape(4, 9, -13, 5, 15, 3),
            Block.createCuboidShape(3, 7, 3, 13, 17, 13),
            Block.createCuboidShape(4, 8, -13, 12, 9, 3),
            Block.createCuboidShape(4, 15, -13, 12, 16, 3),
            Block.createCuboidShape(11, 9, -13, 12, 15, 3)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.createCuboidShape(11, 0, 13, 14, 12, 16),
            Block.createCuboidShape(2, 0, 0, 5, 12, 3),
            Block.createCuboidShape(11, 0, 0, 14, 12, 3),
            Block.createCuboidShape(2, 0, 13, 5, 12, 16),
            Block.createCuboidShape(13, 9, 4, 29, 15, 5),
            Block.createCuboidShape(3, 7, 3, 13, 17, 13),
            Block.createCuboidShape(13, 8, 4, 29, 9, 12),
            Block.createCuboidShape(13, 15, 4, 29, 16, 12),
            Block.createCuboidShape(13, 9, 11, 29, 15, 12)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.createCuboidShape(0, 0, 11, 3, 12, 14),
            Block.createCuboidShape(13, 0, 2, 16, 12, 5),
            Block.createCuboidShape(13, 0, 11, 16, 12, 14),
            Block.createCuboidShape(0, 0, 2, 3, 12, 5),
            Block.createCuboidShape(11, 9, 13, 12, 15, 29),
            Block.createCuboidShape(3, 7, 3, 13, 17, 13),
            Block.createCuboidShape(4, 8, 13, 12, 9, 29),
            Block.createCuboidShape(4, 15, 13, 12, 16, 29),
            Block.createCuboidShape(4, 9, 13, 5, 15, 29)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    private static final VoxelShape SHAPE_W = Stream.of(
            Block.createCuboidShape(2, 0, 0, 5, 12, 3),
            Block.createCuboidShape(11, 0, 13, 14, 12, 16),
            Block.createCuboidShape(2, 0, 13, 5, 12, 16),
            Block.createCuboidShape(11, 0, 0, 14, 12, 3),
            Block.createCuboidShape(-13, 9, 11, 3, 15, 12),
            Block.createCuboidShape(3, 7, 3, 13, 17, 13),
            Block.createCuboidShape(-13, 8, 4, 3, 9, 12),
            Block.createCuboidShape(-13, 15, 4, 3, 16, 12),
            Block.createCuboidShape(-13, 9, 4, 3, 15, 5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
}

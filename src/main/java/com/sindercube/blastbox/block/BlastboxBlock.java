package com.sindercube.blastbox.block;

import com.sindercube.blastbox.sound.BlastboxSoundInstance;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ShriekParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.IntStream;

public class BlastboxBlock extends FacingBlock {
    public static DirectionProperty FACING = Properties.FACING;
    public static BooleanProperty POWERED = Properties.POWERED;

    public BlastboxBlock(Settings settings) {
        super(settings);
        this.setDefaultState(
            this.stateManager.getDefaultState()
                .with(FACING, Direction.SOUTH)
                .with(POWERED, false)
        );
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(POWERED);
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState()
            .with(FACING, ctx.getPlayerLookDirection().getOpposite())
            .with(POWERED, false);
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean powered = world.isReceivingRedstonePower(pos);
        if (state.get(POWERED) == powered) return;
        handlePower(powered, state, world, pos);
    }
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (world.isClient) return;
        boolean powered = world.isReceivingRedstonePower(pos);
        handlePower(powered, state, world, pos);
    }
    public void handlePower(boolean powered, BlockState state, World world, BlockPos pos) {
        if (powered) {
            world.scheduleBlockTick(pos, this, 0);
            world.setBlockState(pos, state.with(POWERED, true));
        } else {
            world.setBlockState(pos, state.with(POWERED, false));
        }
    }
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isReceivingRedstonePower(pos)) return;
        blast(world, pos);
        world.scheduleBlockTick(pos, this, 20);
    }
    public static TagKey<Block> blastboxAmplifiers = TagKey.of(RegistryKeys.BLOCK, new Identifier("blastbox:blastbox_amplifiers"));
    public void playSound(ServerWorld world, BlockPos pos, Direction direction) {
        BlockPos soundBlock = pos.offset(direction.getOpposite());
        Instrument instrument = world.getBlockState(soundBlock).getInstrument();
        if (instrument.isNotBaseBlock()) return;

        float pitch = 1.0F;

        BlockPos amplifierBlock = pos.offset(direction);
        boolean isAmplifier = world.getBlockState(amplifierBlock).isIn(blastboxAmplifiers);
        if (isAmplifier) pitch *= 2;

//        MinecraftClient.getInstance()
//            .getSoundManager()
//            .play(new BlastboxSoundInstance(instrument.getSound().value(), pitch));

//        world.playSound(
//                null,
//                pos.getX()+0.5,
//                pos.getY()+0.5,
//                pos.getZ()+0.5,
//                instrument.getSound().value(),
//                SoundCategory.RECORDS,
//                3.0F,
//                pitch
//        );
    }
    public void blast(ServerWorld world, BlockPos pos) {
        Direction direction = world.getBlockState(pos).get(FACING);
        playSound(world, pos, direction);

        int redstonePower = world.getReceivedRedstonePower(pos);
        int blastPower = (int)Math.ceil((double)redstonePower / 2)+1;
//
//        IntStream.range(1, blastPower).forEach(distance -> {
//
//            BlockPos blastedPos = pos.offset(direction, distance);
//            Box box = new Box(
//                    blastedPos.getX(), blastedPos.getY(), blastedPos.getZ(),
//                    blastedPos.getX()+1, blastedPos.getY()+1, blastedPos.getZ()+1
//            );
//
//            BlockPos particleOffset = BlockPos.ORIGIN.offset(direction, 1);
//            world.spawnParticles(
//                    BLAST_PARTICLE,
//                    blastedPos.getX()+0.5,
//                    blastedPos.getY()+0.5,
//                    blastedPos.getZ()+0.5,
//                    1,
//                    particleOffset.getX(),
//                    particleOffset.getY(),
//                    particleOffset.getZ(),
//                    10
//            );
//
//            List<Entity> hitEntities = world.getNonSpectatingEntities(Entity.class, box);
//
//            int blastPushPower = 8 / distance;
//            hitEntities.forEach(entity -> {
//                Vec3d velocity = Vec3d.ZERO.offset(direction, blastPushPower);
//                entity.setVelocity(velocity.x, entity.getVelocity().y+velocity.y, velocity.z);
//                if (entity.isPlayer()) entity.velocityModified = true;
//            });
//        });
    }
}

package com.sindercube.blastbox.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.entity.WitherSkullEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class BlastProjectile extends PersistentProjectileEntity {
    private int distance;
    private Vec3i direction;
    public BlastProjectile(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        System.out.println("test");
        this.direction = new Vec3i(0, 0, 1);
        this.distance = 20;
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("distance")) {
            this.distance = nbt.getInt("distance") * 20;
        }
        if (nbt.contains("direction")) {
            BlockPos direction = NbtHelper.toBlockPos(nbt.getCompound("direction"));
            this.direction = new Vec3i(direction.getX(), direction.getY(), direction.getZ());
            this.setVelocity(Vec3d.of(this.direction));
            ProjectileUtil.setRotationFromVelocity(this, 1);
        }
    }
    @Override
    public ItemStack asItemStack() { return null; }
    @Override
    public void tick() {
        if (this.getWorld().isClient) return;

        Vec3d velocity = this.getVelocity();
        double d = this.getX() + velocity.x;
        double e = this.getY() + velocity.y;
        double f = this.getZ() + velocity.z;
        // TODO implement movement
//        this.setPosition(d, e, f);

        HitResult hitResult = getCollision();
        if (hitResult.getType() != HitResult.Type.MISS) this.onCollision(hitResult);
    }
    @Override
    protected boolean canHit(Entity entity) {
        if (entity.getClass() == ItemEntity.class) return true;
        return entity.canBeHitByProjectile();
    }
    public HitResult getCollision() {
        HitResult hitResult = this.getWorld().raycast(new RaycastContext(
            this.getPos(),
            this.getPos().add(this.getVelocity()),
            RaycastContext.ShapeType.COLLIDER,
            RaycastContext.FluidHandling.NONE,
            this
        ));
        for (Entity entity : this.getWorld().getOtherEntities(this, this.getBoundingBox(), this::canHit)) {
            hitResult = new EntityHitResult(entity);
        }
        return hitResult;
    }
    @Override
    public void onCollision(HitResult hit) {
        if (hit.getType() == HitResult.Type.BLOCK) {
            blockCollision((BlockHitResult)hit);
        } else if (hit.getType() == HitResult.Type.ENTITY) {
            entityCollision((EntityHitResult)hit);
        }
    }
    public void blockCollision(BlockHitResult hit) {
        System.out.println("blast projectile hit a block");
        BlockPos pos = hit.getBlockPos();
        BlockState state = this.getWorld().getBlockState(pos);
        if (state.getPistonBehavior() != PistonBehavior.DESTROY) return;
        this.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
    }
    public void entityCollision(EntityHitResult hit) {
        Entity entity = hit.getEntity();
        entity.addVelocity(this.direction.getX()*-1, (double)this.direction.getY()/2, this.direction.getZ()*-1);
        if (entity.isPlayer()) entity.velocityModified = true;
    }
}

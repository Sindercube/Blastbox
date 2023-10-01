package com.sindercube.blastbox.entity;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class FullEntityDimensions extends EntityDimensions {
    public final float length;
    public final float width;
    public final float height;
    public final boolean fixed;
    public FullEntityDimensions(float length, float width, float height, boolean fixed) {
        super(width, height, fixed);
        this.length = length;
        this.width = width;
        this.height = height;
        this.fixed = fixed;
    }
    public static FullEntityDimensions fixed(float length, float width, float height) {
        return new FullEntityDimensions(length, width, height, true);
    }
    @Override
    public Box getBoxAt(Vec3d pos) {
        return this.getBoxAt(pos.x, pos.y, pos.z);
    }
    @Override
    public Box getBoxAt(double x, double y, double z) {
        float length = this.length / 2.0F;
        float width = this.width / 2.0F;
        float height = this.height;
        return new Box(
            // TODO possibly swap width and length
            x - (double)length, y,
            z - (double)width, x + (double)length,
            y + (double)height, z + (double)width
        );
    }
}

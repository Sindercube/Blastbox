package com.sindercube.blastbox.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import static net.minecraft.util.math.MathConstants.RADIANS_PER_DEGREE;

public class BlastProjectileModel extends EntityModel<BlastProjectile> {
    public final ModelPart base;
    public final ModelPart cube;

    public BlastProjectileModel(ModelPart part) {
        this.base = part;
        this.cube = part.getChild("cube");
    }
    public void rotate(BlastProjectile entity) {
        this.cube.yaw = entity.getYaw() * RADIANS_PER_DEGREE;
        this.cube.pitch = entity.getPitch() * RADIANS_PER_DEGREE;
    }
    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.base.render(matrices, vertices, light, overlay);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(
                "cube",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-8F, -8F, 0F, 16F, 16F, 0F)
                        .mirrored(false),
                ModelTransform.pivot(0, -8, 0)
        );

        return TexturedModelData.of(modelData, 32, 16);
    }

    @Override
    public void setAngles(BlastProjectile entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {}
}

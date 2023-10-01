package com.sindercube.blastbox.entity;

import com.sindercube.blastbox.BlastboxClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import static com.sindercube.blastbox.Blastbox.MOD_ID;

public class BlastProjectileRenderer extends EntityRenderer<BlastProjectile> {
    public static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/entity/blast.png");
    public final BlastProjectileModel model;

    public BlastProjectileRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new BlastProjectileModel(context.getPart(BlastboxClient.BLAST_PROJECTILE_LAYER));
    }
    @Override
    public void render(BlastProjectile entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.getLayer(this.getTexture(entity)));
        this.model.rotate(entity);
        this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }
    @Override
    public int getBlockLight(BlastProjectile entity, BlockPos pos) {
        return 15;
    }
    @Override
    public Identifier getTexture(BlastProjectile entity) {
        return TEXTURE;
    }
}

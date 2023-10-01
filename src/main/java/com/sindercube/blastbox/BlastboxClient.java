package com.sindercube.blastbox;

import com.sindercube.blastbox.entity.BlastProjectileModel;
import com.sindercube.blastbox.entity.BlastProjectileRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.ItemGroups;
import net.minecraft.util.Identifier;

public class BlastboxClient implements ClientModInitializer {
    public static final EntityModelLayer BLAST_PROJECTILE_LAYER = new EntityModelLayer(
            new Identifier(Blastbox.MOD_ID, "blast"),
            "main"
    );
    @Override
    public void onInitializeClient() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
            content.add(Blastbox.BLASTBOX_ITEM);
        });
        EntityRendererRegistry.register(Blastbox.BLAST_PROJECTILE, BlastProjectileRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BLAST_PROJECTILE_LAYER, BlastProjectileModel::getTexturedModelData);
    }
}

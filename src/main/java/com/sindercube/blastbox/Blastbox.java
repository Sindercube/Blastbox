package com.sindercube.blastbox;

import com.sindercube.blastbox.block.BlastboxBlock;
import com.sindercube.blastbox.entity.BlastProjectile;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Blastbox implements ModInitializer {
    public static final String MOD_ID = "blastbox";
    public static final Logger LOGGER = LoggerFactory.getLogger("blastbox");

    public static final Block BLASTBOX_BLOCK = Registry.register(
            Registries.BLOCK,
            new Identifier(MOD_ID, "blastbox"),
            new BlastboxBlock(FabricBlockSettings.create().strength(3.5f).hardness(3.5F).requiresTool())
    );
    public static final Item BLASTBOX_ITEM = Registry.register(
            Registries.ITEM,
            new Identifier(MOD_ID, "blastbox"),
            new BlockItem(BLASTBOX_BLOCK, new FabricItemSettings())
    );
//    public static final BlockEntityType<BlastboxBlockEntity> BLASTBOX_BLOCK_ENTITY = Registry.register(
//            Registries.BLOCK_ENTITY_TYPE,
//            new Identifier(MODID, "blastbox"),
//            FabricBlockEntityTypeBuilder.create(BlastboxBlockEntity::new, BLASTBOX_BLOCK).build()
//    );
    public static final EntityType<BlastProjectile> BLAST_PROJECTILE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MOD_ID, "blast"),
            FabricEntityTypeBuilder.<BlastProjectile>create(SpawnGroup.MISC, BlastProjectile::new)
                    .disableSaving()
                    .fireImmune()
                    .dimensions(EntityDimensions.fixed(1, 1))
//                    .dimensions(FullEntityDimensions.fixed(0, 1, 1))
                    .build()
    );
    @Override
    public void onInitialize() {
        LOGGER.info("Blastbox initiated!");
    }
}
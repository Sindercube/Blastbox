package com.sindercube.blastbox.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;

//import static com.sindercube.blastbox.Blastbox.BLASTBOX_BLOCK_ENTITY;

public class BlastboxBlockEntity extends BlockEntity {
    public BlastboxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
//      super(BLASTBOX_BLOCK_ENTITY, pos, state);
        super(type, pos, state);
    }
}

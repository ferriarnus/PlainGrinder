package com.lothrazar.plaingrinder.create;

import com.lothrazar.plaingrinder.grind.TileGrinder;
import com.simibubi.create.content.contraptions.components.crank.HandCrankBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CreateCompat {

    public static void handcrank(BlockEntity entity, BlockState state) {
        if (state.getBlock() instanceof HandCrankBlock && entity instanceof TileGrinder grinder) {
            grinder.incrementGrind();
        }
    }
}

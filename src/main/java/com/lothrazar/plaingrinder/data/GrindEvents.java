package com.lothrazar.plaingrinder.data;

import com.lothrazar.plaingrinder.ModRegistry;
import com.lothrazar.plaingrinder.grind.TileGrinder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GrindEvents {

  @SubscribeEvent
  public void onHit(PlayerInteractEvent.RightClickBlock event) {
    if (event.getHand() == InteractionHand.OFF_HAND) {
      return;
    }
    BlockPos pos = event.getPos();
    Player player = event.getPlayer();
    Level world = player.getCommandSenderWorld();
    BlockState state = world.getBlockState(pos);
    if (state.getBlock() == ModRegistry.B_HANDLE) {
      BlockState below = world.getBlockState(pos.below());
      if (below.getBlock() == ModRegistry.B_GRINDER) {
        //do the thing
        TileGrinder tile = (TileGrinder) world.getBlockEntity(pos.below());
        if (tile.canGrind()) {
          // and state
          Direction old = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
          world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.HORIZONTAL_FACING, old.getCounterClockWise()));
          tile.incrementGrind();
          player.swing(event.getHand());
        }
      }
    }
  }
}
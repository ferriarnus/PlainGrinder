package com.lothrazar.plaingrinder.data;

import com.lothrazar.plaingrinder.ModRegistry;
import com.lothrazar.plaingrinder.grind.TileGrinder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GrindEvents {

  @SubscribeEvent
  public void onHit(PlayerInteractEvent.RightClickBlock event) {
    PlayerEntity player = event.getPlayer();
    ItemStack held = player.getHeldItem(event.getHand());
    World world = player.getEntityWorld();
    if (!held.isEmpty() || event.getHand() == Hand.OFF_HAND) {
      return;
    }
    BlockPos pos = event.getPos();
    BlockState state = world.getBlockState(pos);
    if (state.getBlock() == ModRegistry.B_HANDLE) {
      //unmapped rotate function
      //    state = state.func_235896_a_(BlockStateProperties.HORIZONTAL_FACING);
      // problem 1: its too fast
      //problem 2: should be 4x4 base to centralize
      BlockState below = world.getBlockState(pos.down());
      if (below.getBlock() == ModRegistry.B_GRINDER) {
        //do the thing
        TileGrinder tile = (TileGrinder) world.getTileEntity(pos.down());
        if (tile.canGrind()) {
          //can we?
          tile.incrementGrind();
          // and state
          if (world.isRemote == false) {
            Direction old = state.get(BlockStateProperties.HORIZONTAL_FACING);
            world.setBlockState(pos, state.with(BlockStateProperties.HORIZONTAL_FACING, old.rotateYCCW()));
          }
          player.swingArm(event.getHand());
        }
      }
    }
  }
}

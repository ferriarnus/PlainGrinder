package com.lothrazar.plaingrinder;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GrindEvents {

  @SubscribeEvent
  public void onHit(PlayerInteractEvent.RightClickBlock event) {
    PlayerEntity player = event.getPlayer();
    ItemStack held = player.getHeldItem(event.getHand());
    World world = player.getEntityWorld();
    if (!held.isEmpty() || world.isRemote || event.getHand() == Hand.OFF_HAND) {
      return;
    }
    BlockState state = world.getBlockState(event.getPos());
    if (state.getBlock() == ModRegistry.B_HANDLE) {
      //unmapped rotate function
      //    state = state.func_235896_a_(BlockStateProperties.HORIZONTAL_FACING);
      // problem 1: its too fast
      //problem 2: should be 4x4 base to centralize
      Direction old = state.get(BlockStateProperties.HORIZONTAL_FACING);
      world.setBlockState(event.getPos(), state.with(BlockStateProperties.HORIZONTAL_FACING, old.rotateYCCW()));
    }
  }
}

package com.lothrazar.plaingrinder.data;

import com.lothrazar.plaingrinder.ModRegistry;
import com.lothrazar.plaingrinder.grind.TileGrinder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GrindEvents {

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  static void onHit(PlayerInteractEvent.RightClickBlock event) {
    if (event.getHand() == InteractionHand.OFF_HAND) {
      return;
    }
    BlockPos pos = event.getPos();
    Player player = event.getPlayer();
    Level world = player.getCommandSenderWorld();
    BlockState state = world.getBlockState(pos);
    if (state.is(ModRegistry.CRANKS) && world.getBlockEntity(pos.below()) instanceof TileGrinder grinder) {
      grinder.incrementGrind();
    }
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
          player.playSound(SoundEvents.WOODEN_BUTTON_CLICK_ON, 0.3F, 0.5F);
        }
      }
    }
  }
}
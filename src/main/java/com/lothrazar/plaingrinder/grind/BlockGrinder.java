package com.lothrazar.plaingrinder.grind;

import com.lothrazar.plaingrinder.ModRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class BlockGrinder extends BaseEntityBlock {

  public BlockGrinder(Properties properties) {
    super(properties);
  }

  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new TileGrinder(pos, state);
  }

  @Override
  public RenderShape getRenderShape(BlockState bs) {
    return RenderShape.MODEL;
  }

  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
    return createTickerHelper(type, ModRegistry.T_GRINDER, (p_155253_, p_155254_, p_155255_, p_155256_) -> p_155256_.tick());
  }

  @Override
  public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
    if (!world.isClientSide) {
      BlockEntity tileEntity = world.getBlockEntity(pos);
      if (tileEntity instanceof MenuProvider) {
        NetworkHooks.openGui((ServerPlayer) player, (MenuProvider) tileEntity, tileEntity.getBlockPos());
      }
      else {
        throw new IllegalStateException("Our named container provider is missing!");
      }
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
    if (state.getBlock() != newState.getBlock()) {
      BlockEntity tileentity = worldIn.getBlockEntity(pos);
      if (tileentity instanceof TileGrinder) {
        TileGrinder grinder = (TileGrinder) tileentity;
        for (int i = 0; i < grinder.inputSlots.getSlots(); ++i) {
          Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), grinder.inputSlots.getStackInSlot(i));
        }
        for (int i = 0; i < grinder.outputSlots.getSlots(); ++i) {
          Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), grinder.outputSlots.getStackInSlot(i));
        }
        worldIn.updateNeighbourForOutputSignal(pos, this);
      }
      super.onRemove(state, worldIn, pos, newState, isMoving);
    }
  }

  @Override
  public void appendHoverText(ItemStack stack, BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
    TranslatableComponent t = new TranslatableComponent(getDescriptionId() + ".tooltip");
    tooltip.add(t.withStyle(ChatFormatting.GRAY));
  }
}
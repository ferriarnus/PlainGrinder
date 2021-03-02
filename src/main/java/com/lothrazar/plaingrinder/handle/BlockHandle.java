package com.lothrazar.plaingrinder.handle;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockHandle extends Block {

  private static final double BOUNDS = 6;
  private static final VoxelShape AABB = Block.makeCuboidShape(BOUNDS, 0, BOUNDS,
      16 - BOUNDS, 12, 16 - BOUNDS);

  public BlockHandle(Properties properties) {
    super(properties.notSolid());
  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
    return AABB;
  }

  @Override
  @OnlyIn(Dist.CLIENT)
  public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    TranslationTextComponent t = new TranslationTextComponent(getTranslationKey() + ".tooltip");
    tooltip.add(t.mergeStyle(TextFormatting.GRAY));
  }

  @Override
  public void onBlockPlacedBy(World world, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack) {
    if (entity != null) {
      world.setBlockState(pos, state.with(BlockStateProperties.HORIZONTAL_FACING, getFacingFromEntity(pos, entity)), 2);
    }
  }

  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
    builder.add(BlockStateProperties.HORIZONTAL_FACING);
  }

  public static Direction getFacingFromEntity(BlockPos clickedBlock, LivingEntity entity) {
    Direction d = Direction.getFacingFromVector(
        (float) (entity.lastTickPosX - clickedBlock.getX()),
        (float) (entity.lastTickPosY - clickedBlock.getY()),
        (float) (entity.lastTickPosZ - clickedBlock.getZ()));
    //if only horizontal is allowed
    if (d == Direction.UP || d == Direction.DOWN) {
      return entity.getHorizontalFacing().getOpposite();
    }
    return d;
  }
}

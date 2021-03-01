package com.lothrazar.plaingrinder.grind;

import com.lothrazar.plaingrinder.ModRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerGrinder extends Container {

  public static final int PLAYERSIZE = 4 * 9;
  protected int startInv = 0;
  protected int endInv = 0;
  private TileGrinder tile;

  public ContainerGrinder(int windowId, World world, BlockPos pos, PlayerInventory inv, PlayerEntity player) {
    this(ModRegistry.CTR_GRINDER, windowId);
    tile = (TileGrinder) world.getTileEntity(pos);
    //    this.playerEntity = player; 
    addSlot(new SlotItemHandler(tile.inputSlots, 0, 24, 40));
  }

  public ContainerGrinder(ContainerType<ContainerGrinder> ctrgrinder, int windowId) {
    super(ctrgrinder, windowId);
  }

  @Override
  public boolean canInteractWith(PlayerEntity playerIn) {
    return true;
  }

  @Override
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
    try {
      //if last machine slot is 17, endInv is 18
      int playerStart = endInv;
      int playerEnd = endInv + PLAYERSIZE; //53 = 17 + 36  
      //standard logic based on start/end
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.inventorySlots.get(index);
      if (slot != null && slot.getHasStack()) {
        ItemStack stack = slot.getStack();
        itemstack = stack.copy();
        if (index < this.endInv) {
          if (!this.mergeItemStack(stack, playerStart, playerEnd, false)) {
            return ItemStack.EMPTY;
          }
        }
        else if (index <= playerEnd && !this.mergeItemStack(stack, startInv, endInv, false)) {
          return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
          slot.putStack(ItemStack.EMPTY);
        }
        else {
          slot.onSlotChanged();
        }
        if (stack.getCount() == itemstack.getCount()) {
          return ItemStack.EMPTY;
        }
        slot.onTake(playerIn, stack);
      }
      return itemstack;
    }
    catch (Exception e) {
      //      ModCyclic.LOGGER.error("Shift click error", e);
      return ItemStack.EMPTY;
    }
  }
}

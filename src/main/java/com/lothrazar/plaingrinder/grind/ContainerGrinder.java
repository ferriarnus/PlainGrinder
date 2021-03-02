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
  protected int endInv = 2;
  private TileGrinder tile;
  protected PlayerEntity playerEntity;
  protected PlayerInventory playerInventory;

  public ContainerGrinder(int windowId, World world, BlockPos pos, PlayerInventory inv, PlayerEntity player) {
    this(ModRegistry.CTR_GRINDER, windowId);
    this.playerEntity = player;
    this.playerInventory = inv;
    tile = (TileGrinder) world.getTileEntity(pos);
    addSlot(new SlotItemHandler(tile.inputSlots, 0, 24, 40));
    addSlot(new SlotItemHandler(tile.outputSlots, 0, 74, 40));
    layoutPlayerInventorySlots(8, 84);
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
      return ItemStack.EMPTY;
    }
  }

  private int addSlotRange(PlayerInventory handler, int index, int x, int y, int amount, int dx) {
    for (int i = 0; i < amount; i++) {
      addSlot(new Slot(handler, index, x, y));
      x += dx;
      index++;
    }
    return index;
  }

  private int addSlotBox(PlayerInventory handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
    for (int j = 0; j < verAmount; j++) {
      index = addSlotRange(handler, index, x, y, horAmount, dx);
      y += dy;
    }
    return index;
  }

  protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
    // Player inventory
    addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
    // Hotbar
    topRow += 58;
    addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
  }
}

package com.lothrazar.plaingrinder.grind;

import com.lothrazar.plaingrinder.ModRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerGrinder extends AbstractContainerMenu {

  public static final int PLAYERSIZE = 4 * 9;
  protected int startInv = 0;
  protected int endInv = 2;
  protected Inventory playerInventory;

  public ContainerGrinder(int id, Inventory inv, FriendlyByteBuf extraData) {
    this(id, inv, (BlockEntityGrinder) inv.player.level.getBlockEntity(extraData.readBlockPos()));
  }

  //OLD(int windowId, Level world, BlockPos pos, Inventory inv, Player player) {
  public ContainerGrinder(int windowId, Inventory inv, BlockEntityGrinder tile) {
    super(ModRegistry.MENU.get(), windowId);
    this.playerInventory = inv;
    addSlot(new SlotItemHandler(tile.inputSlots, 0, 55, 35));
    addSlot(new SlotItemHandler(tile.outputSlots, 0, 109, 35));
    layoutPlayerInventorySlots(8, 84);
  }

  @Override
  public boolean stillValid(Player playerIn) {
    return true;
  }

  @Override
  public ItemStack quickMoveStack(Player playerIn, int index) {
    try {
      //if last machine slot is 17, endInv is 18
      int playerStart = endInv;
      int playerEnd = endInv + PLAYERSIZE; //53 = 17 + 36  
      //standard logic based on start/end
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(index);
      if (slot != null && slot.hasItem()) {
        ItemStack stack = slot.getItem();
        itemstack = stack.copy();
        if (index < this.endInv) {
          if (!this.moveItemStackTo(stack, playerStart, playerEnd, false)) {
            return ItemStack.EMPTY;
          }
        }
        else if (index <= playerEnd && !this.moveItemStackTo(stack, startInv, endInv, false)) {
          return ItemStack.EMPTY;
        }
        if (stack.isEmpty()) {
          slot.set(ItemStack.EMPTY);
        }
        else {
          slot.setChanged();
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

  private int addSlotRange(Inventory handler, int index, int x, int y, int amount, int dx) {
    for (int i = 0; i < amount; i++) {
      addSlot(new Slot(handler, index, x, y));
      x += dx;
      index++;
    }
    return index;
  }

  private int addSlotBox(Inventory handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
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
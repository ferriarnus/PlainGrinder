package com.lothrazar.plaingrinder.grind;

import com.lothrazar.plaingrinder.ConfigManager;
import com.lothrazar.plaingrinder.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ContainerGrinder extends AbstractContainerMenu {

  public static final int PLAYERSIZE = 4 * 9;
  protected int startInv = 0;
  protected int endInv = 3;
  private TileGrinder tile;
  protected Player playerEntity;
  protected Inventory playerInventory;

  public ContainerGrinder(int windowId, Level world, BlockPos pos, Inventory inv, Player player) {
    this(ModRegistry.CTR_GRINDER, windowId);
    this.playerEntity = player;
    this.playerInventory = inv;
    tile = (TileGrinder) world.getBlockEntity(pos);
    addSlot(new SlotItemHandler(tile.inputSlots, 0, 55, 35));
    addSlot(new SlotItemHandler(tile.outputSlots, 0, 109, 35) {
      @Override
      public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
      }
    });
    addSlot(new SlotItemHandler(tile.outputSlots, 1, 129, 35) {
      @Override
      public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
      }
    });
    layoutPlayerInventorySlots(8, 84);
  }

  public ContainerGrinder(MenuType<ContainerGrinder> ctrgrinder, int windowId) {
    super(ctrgrinder, windowId);
  }

  @Override
  public boolean stillValid(Player playerIn) {
    return true;
  }

  @Override
  public ItemStack quickMoveStack(Player playerIn, int index) {
    try {
      //if last machine slot is 18, endInv is 19
      int playerStart = endInv;
      int playerEnd = endInv + PLAYERSIZE; //54 = 18 + 36
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

  public float percentageWork() {
    return ((float)tile.getStage())/ (tile.getMaxStage());
  }
}
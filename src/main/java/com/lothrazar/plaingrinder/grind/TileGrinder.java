package com.lothrazar.plaingrinder.grind;

import com.lothrazar.plaingrinder.ModMain;
import com.lothrazar.plaingrinder.ModRegistry;
import com.lothrazar.plaingrinder.data.ItemStackHandlerWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileGrinder extends TileEntity implements INamedContainerProvider, ITickableTileEntity, IInventory {

  private static final int TIMER_DONE = 5;
  private static final int STAGE_DONE = 4;
  public static final String NBTINV = "inv";
  ItemStackHandler inputSlots = new ItemStackHandler(1);
  ItemStackHandler outputSlots = new ItemStackHandler(1);
  private ItemStackHandlerWrapper inventory = new ItemStackHandlerWrapper(inputSlots, outputSlots);
  private LazyOptional<IItemHandler> inventoryCap = LazyOptional.of(() -> inventory);
  private int stage = 0;
  private int timer = 0;

  public TileGrinder() {
    super(ModRegistry.T_GRINDER);
  }

  @Override
  public void tick() {
    timer--;
    if (timer < 0) {
      timer = 0;
    }
    //do we process
    if (stage == STAGE_DONE) {
      this.doProcess();
    }
  }

  private void doProcess() {
    stage = 0;
    ItemStack input = this.inputSlots.getStackInSlot(0);
    ModMain.LOGGER.info("doProcess complete, stage reset" + input);
    if (input.isEmpty()) {
      return;
    }
  }

  @Override
  public void read(BlockState bs, CompoundNBT tag) {
    inventory.deserializeNBT(tag.getCompound(NBTINV));
    stage = tag.getInt("grindstage");
    timer = tag.getInt("timer");
    super.read(bs, tag);
  }

  @Override
  public CompoundNBT write(CompoundNBT tag) {
    tag.put(NBTINV, inventory.serializeNBT());
    tag.putInt("grindstage", stage);
    tag.putInt("timer", timer);
    return super.write(tag);
  }

  @Override
  public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return inventoryCap.cast();
    }
    return super.getCapability(cap, side);
  }

  @Override
  public ITextComponent getDisplayName() {
    return new StringTextComponent(getType().getRegistryName().getPath());
  }

  @Override
  public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
    return new ContainerGrinder(i, world, pos, playerInventory, playerEntity);
  }

  public void incrementGrind() {
    timer = TIMER_DONE; //restart to allow another rotation
    stage++;
    if (stage > STAGE_DONE) {
      stage = STAGE_DONE;
    }
  }

  public boolean canGrind() {
    return timer == 0;
  }

  /******** Fakeout stuff for IRecipe *********************/
  @Override
  public void clear() {
    // TODO Auto-generated method stub
  }

  @Override
  public ItemStack decrStackSize(int arg0, int arg1) {
    return ItemStack.EMPTY;
  }

  @Override
  public int getSizeInventory() {
    return 0;
  }

  @Override
  public ItemStack getStackInSlot(int arg0) {
    return ItemStack.EMPTY;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public boolean isUsableByPlayer(PlayerEntity arg0) {
    return true;
  }

  @Override
  public ItemStack removeStackFromSlot(int arg0) {
    return ItemStack.EMPTY;
  }

  @Override
  public void setInventorySlotContents(int arg0, ItemStack arg1) {}
}

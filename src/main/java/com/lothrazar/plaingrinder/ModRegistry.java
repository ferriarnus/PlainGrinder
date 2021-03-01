package com.lothrazar.plaingrinder;

import com.lothrazar.plaingrinder.grind.ContainerGrinder;
import com.lothrazar.plaingrinder.grind.BlockGrinder;
import com.lothrazar.plaingrinder.grind.TileGrinder;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  public static final ItemGroup GROUP = new ItemGroup(ModMain.MODID) {

    @Override
    public ItemStack createIcon() {
      return new ItemStack(ModRegistry.blockgrinder);
    }
  };
  //change Object to your Block/Item/whatever 
  @ObjectHolder(ModMain.MODID + ":grinder")
  public static Block blockgrinder;
  @ObjectHolder(ModMain.MODID + ":grinder")
  public static ContainerType<ContainerGrinder> ctrgrinder;
  @ObjectHolder(ModMain.MODID + ":grinder")
  public static TileEntityType<TileGrinder> tilegrinder;

  @SubscribeEvent
  public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
    IForgeRegistry<Block> r = event.getRegistry();
    r.register(new BlockGrinder(Block.Properties.create(Material.ROCK)).setRegistryName("grinder"));
  }

  @SubscribeEvent
  public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event) {
    IForgeRegistry<TileEntityType<?>> r = event.getRegistry();
    r.register(TileEntityType.Builder.create(TileGrinder::new, blockgrinder).build(null).setRegistryName("grinder"));
  }

  @SubscribeEvent
  public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
    IForgeRegistry<ContainerType<?>> r = event.getRegistry();
    r.register(IForgeContainerType.create((windowId, inv, data) -> {
      return new ContainerGrinder(windowId, inv.player.world, data.readBlockPos(), inv, inv.player);
    }).setRegistryName("grinder"));
  }

  @SubscribeEvent
  public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> r = event.getRegistry();
    r.register(new BlockItem(blockgrinder, new Item.Properties().group(GROUP)).setRegistryName("grinder"));
  }

  @SubscribeEvent
  public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
    //  IForgeRegistry<SoundEvent> r = event.getRegistry();
  }
}

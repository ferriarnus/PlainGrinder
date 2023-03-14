package com.lothrazar.plaingrinder;

import com.lothrazar.plaingrinder.grind.BlockGrinder;
import com.lothrazar.plaingrinder.grind.ContainerGrinder;
import com.lothrazar.plaingrinder.grind.TileGrinder;
import com.lothrazar.plaingrinder.handle.BlockHandle;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  public static final CreativeModeTab GROUP = new CreativeModeTab(ModMain.MODID) {

    @Override
    public ItemStack makeIcon() {
      return new ItemStack(ModRegistry.B_HANDLE);
    }
  };
  @ObjectHolder(ModMain.MODID + ":handle")
  public static Block B_HANDLE;
  @ObjectHolder(ModMain.MODID + ":grinder")
  public static Block B_GRINDER;
  @ObjectHolder(ModMain.MODID + ":grinder")
  public static MenuType<ContainerGrinder> CTR_GRINDER;
  @ObjectHolder(ModMain.MODID + ":grinder")
  public static BlockEntityType<TileGrinder> T_GRINDER;

  @SubscribeEvent
  public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
    IForgeRegistry<Block> r = event.getRegistry();
    r.register(new BlockGrinder(Block.Properties.of(Material.STONE).strength(0.9F)).setRegistryName("grinder"));
    r.register(new BlockHandle(Block.Properties.of(Material.WOOD).strength(0.4F)).setRegistryName("handle"));
  }

  @SubscribeEvent
  public static void onTileEntityRegistry(final RegistryEvent.Register<BlockEntityType<?>> event) {
    IForgeRegistry<BlockEntityType<?>> r = event.getRegistry();
    r.register(BlockEntityType.Builder.of(TileGrinder::new, B_GRINDER).build(null).setRegistryName("grinder"));
  }

  @SubscribeEvent
  public static void onContainerRegistry(final RegistryEvent.Register<MenuType<?>> event) {
    IForgeRegistry<MenuType<?>> r = event.getRegistry();
    r.register(IForgeMenuType.create((windowId, inv, data) -> {
      return new ContainerGrinder(windowId, inv.player.level, data.readBlockPos(), inv, inv.player);
    }).setRegistryName("grinder"));
  }

  @SubscribeEvent
  public static void onItemsRegistry(final RegistryEvent.Register<Item> event) {
    IForgeRegistry<Item> r = event.getRegistry();
    r.register(new BlockItem(B_GRINDER, new Item.Properties().tab(GROUP)).setRegistryName("grinder"));
    r.register(new BlockItem(B_HANDLE, new Item.Properties().tab(GROUP)).setRegistryName("handle"));
    r.register(new ItemDustBurnable(new Item.Properties().tab(GROUP)).setRegistryName("dust_coal"));
    r.register(new Item(new Item.Properties().tab(GROUP)).setRegistryName("dust_diamond"));
    r.register(new Item(new Item.Properties().tab(GROUP)).setRegistryName("dust_gold"));
    r.register(new Item(new Item.Properties().tab(GROUP)).setRegistryName("dust_iron"));
    r.register(new Item(new Item.Properties().tab(GROUP)).setRegistryName("dust_emerald"));
    r.register(new Item(new Item.Properties().tab(GROUP)).setRegistryName("dust_lapis"));
    r.register(new Item(new Item.Properties().tab(GROUP)).setRegistryName("dust_copper"));
    r.register(new ItemDustBurnable(new Item.Properties().tab(GROUP)).setRegistryName("dust_charcoal"));
    r.register(new Item(new Item.Properties().tab(GROUP)).setRegistryName("dust_quartz"));
  }

  public static final TagKey<Block> CRANKS = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModMain.MODID, "cranks"));
}
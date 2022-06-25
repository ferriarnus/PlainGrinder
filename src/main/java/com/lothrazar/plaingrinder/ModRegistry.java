package com.lothrazar.plaingrinder;

import com.lothrazar.plaingrinder.grind.BlockGrinder;
import com.lothrazar.plaingrinder.grind.ContainerGrinder;
import com.lothrazar.plaingrinder.grind.GrindRecipe;
import com.lothrazar.plaingrinder.grind.ModRecipeType;
import com.lothrazar.plaingrinder.grind.TileGrinder;
import com.lothrazar.plaingrinder.handle.BlockHandle;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

  static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);
  static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MODID);
  static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ModMain.MODID);
  static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ModMain.MODID);
  public static final CreativeModeTab TAB = new CreativeModeTab(ModMain.MODID) {

    @Override
    public ItemStack makeIcon() {
      return new ItemStack(ihandle.get());
    }
  };
  public static final RegistryObject<Block> GRINDER = BLOCKS.register("grinder", () -> new BlockGrinder(Block.Properties.of(Material.STONE).strength(0.9F)));
  public static final RegistryObject<Block> handle = BLOCKS.register("handle", () -> new BlockHandle(Block.Properties.of(Material.WOOD).strength(0.4F)));
  public static final RegistryObject<Item> igrinder = ITEMS.register("grinder", () -> new BlockItem(GRINDER.get(), new Item.Properties().tab(TAB)));
  public static final RegistryObject<Item> ihandle = ITEMS.register("handle", () -> new BlockItem(handle.get(), new Item.Properties().tab(TAB)));
  public static final RegistryObject<BlockEntityType<TileGrinder>> TE_GRINDER = TILES.register("grinder", () -> BlockEntityType.Builder.of(TileGrinder::new, GRINDER.get()).build(null));

  //  @SubscribeEvent
  //  public static void onContainerRegistry(final RegistryEvent.Register<MenuType<?>> event) {
  //    IForgeRegistry<MenuType<?>> r = event.getRegistry();
  //    r.register(IForgeMenuType.create((windowId, inv, data) -> {
  //      return new ContainerGrinder(windowId, inv.player.level, data.readBlockPos(), inv, inv.player);
  //    }).setRegistryName("grinder"));
  //  }
  @SubscribeEvent
  public static void onBlocksRegistry(RegisterEvent event) {
    event.register(ForgeRegistries.Keys.RECIPE_TYPES, r -> {
      r.register("grinder", ModRecipeType.GRIND);
    });
    event.register(ForgeRegistries.Keys.RECIPE_SERIALIZERS, r -> {
      //
      //      Registry.register(Registry.RECIPE_TYPE, ModRecipeType.GRIND.toString(), ModRecipeType.GRIND);
      r.register("grinder", GrindRecipe.SERIALIZER);
    });
  }

  public static final RegistryObject<MenuType<ContainerGrinder>> MENU = CONTAINERS.register("grinder", () -> IForgeMenuType.create(ContainerGrinder::new));
  ///
  public static final RegistryObject<Item> dust_coal = ITEMS.register("dust_coal", () -> new ItemDustBurnable(new Item.Properties().tab(TAB)));
  public static final RegistryObject<Item> dust_charcoal = ITEMS.register("dust_charcoal", () -> new ItemDustBurnable(new Item.Properties().tab(TAB)));
  public static final RegistryObject<Item> dust_diamond = ITEMS.register("dust_diamond", () -> new Item(new Item.Properties().tab(TAB)));
  public static final RegistryObject<Item> dust_gold = ITEMS.register("dust_gold", () -> new Item(new Item.Properties().tab(TAB)));
  public static final RegistryObject<Item> dust_iron = ITEMS.register("dust_iron", () -> new Item(new Item.Properties().tab(TAB)));
  public static final RegistryObject<Item> dust_emerald = ITEMS.register("dust_emerald", () -> new Item(new Item.Properties().tab(TAB)));
  public static final RegistryObject<Item> dust_lapis = ITEMS.register("dust_lapis", () -> new Item(new Item.Properties().tab(TAB)));
  public static final RegistryObject<Item> dust_copper = ITEMS.register("dust_copper", () -> new Item(new Item.Properties().tab(TAB)));
  public static final RegistryObject<Item> dust_quartz = ITEMS.register("dust_quartz", () -> new Item(new Item.Properties().tab(TAB)));
}
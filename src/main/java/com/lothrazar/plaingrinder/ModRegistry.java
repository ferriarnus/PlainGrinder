package com.lothrazar.plaingrinder;

import com.lothrazar.plaingrinder.grind.BlockGrinder;
import com.lothrazar.plaingrinder.grind.ContainerGrinder;
import com.lothrazar.plaingrinder.grind.GrindRecipe;
import com.lothrazar.plaingrinder.grind.GrindRecipe.SerializeGrinderRecipe;
import com.lothrazar.plaingrinder.grind.BlockEntityGrinder;
import com.lothrazar.plaingrinder.handle.BlockHandle;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRegistry {

  static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);
  static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModMain.MODID);
  static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ModMain.MODID);
  static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ModMain.MODID);
  static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ModMain.MODID);
  static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, ModMain.MODID);
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
  //block entity and container
  public static final RegistryObject<BlockEntityType<BlockEntityGrinder>> TE_GRINDER = TILES.register("grinder", () -> BlockEntityType.Builder.of(BlockEntityGrinder::new, GRINDER.get()).build(null));
  public static final RegistryObject<MenuType<ContainerGrinder>> MENU = CONTAINERS.register("grinder", () -> IForgeMenuType.create(ContainerGrinder::new));
  //two for the recipe
  public static final RegistryObject<RecipeType<GrindRecipe>> GRINDER_RECIPE_TYPE = RECIPE_TYPES.register("grinder", () -> new RecipeType<GrindRecipe>() {
    //yep leave it empty its fine
  });
  public static final RegistryObject<SerializeGrinderRecipe> GRINDER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("grinder", SerializeGrinderRecipe::new);
  //items
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
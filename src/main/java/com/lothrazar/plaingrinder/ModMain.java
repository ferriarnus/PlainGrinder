package com.lothrazar.plaingrinder;

import com.lothrazar.plaingrinder.data.GrindEvents;
import com.lothrazar.plaingrinder.grind.GrindRecipe;
import com.lothrazar.plaingrinder.grind.ModRecipeType;
import com.lothrazar.plaingrinder.grind.ScreenGrinder;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModMain.MODID)
public class ModMain {

  public static final String MODID = "plaingrinder";
  public static final Logger LOGGER = LogManager.getLogger();

  public ModMain() {
    ConfigManager.setup();
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    //https://github.com/Minecraft-Forge-Tutorials/Custom-Json-Recipes/blob/master/src/main/java/net/darkhax/customrecipeexample/CustomRecipesMod.java
    FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, ModMain::registerRecipeSerializers);
  }

  //todo: mekanism and thermal built in support
  //3x ores in mystical ag - direct recipes
  //ex nihilo ore chunks
  private void setup(final FMLCommonSetupEvent event) {
    //now all blocks/items exist  
    MinecraftForge.EVENT_BUS.register(new GrindEvents());
  }

  private void setupClient(final FMLClientSetupEvent event) {
    //for client side only setup
    ScreenManager.registerFactory(ModRegistry.CTR_GRINDER, ScreenGrinder::new);
  }

  public static void registerRecipeSerializers(Register<IRecipeSerializer<?>> event) {
    Registry.register(Registry.RECIPE_TYPE, ModRecipeType.GRIND.toString(), ModRecipeType.GRIND);
    event.getRegistry().register(GrindRecipe.SERIALIZER);
  }
}

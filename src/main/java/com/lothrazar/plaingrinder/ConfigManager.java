package com.lothrazar.plaingrinder;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.fml.loading.FMLPaths;

public class ConfigManager {

  private static final ForgeConfigSpec.Builder CFG = new ForgeConfigSpec.Builder();
  private static ForgeConfigSpec COMMON_CONFIG;
  public static IntValue TIMER_COOLDOWN;
  public static IntValue MAX_STAGE;
  public static BooleanValue BREAKABLE_HANDLE;
  public static BooleanValue AUTOMATION_ALLOWED;

  static {
    initConfig();
  }

  private static void initConfig() {
    CFG.comment("General settings").push(ModMain.MODID);
    AUTOMATION_ALLOWED = CFG.comment("\r\nTrue means automation is allowed as normal; false will disable the capabilities so hoppers, cables, pipes will not connect")
        .define("allowAutomation", true);
    BREAKABLE_HANDLE = CFG.comment("\r\nCan the handle break if its used too many times while the input is empty")
        .define("breakableHandle", true);
    TIMER_COOLDOWN = CFG.comment("\r\nHow many ticks must be in between two player interactions; this is the cooldown in between spins at maximum speed (20 ticks in one second)")
        .defineInRange("timerCooldown", 5, 1, 80);
    MAX_STAGE = CFG.comment("\r\nHow many stages (player uses) trigger a recipe. Each stage is one cardinal direction, meaning 4 stages is one full rotation")
        .defineInRange("stagesPerRecipe", 4, 1, 444);
    CFG.pop(); // one pop for every push
    COMMON_CONFIG = CFG.build();
  }

  public static void setup() {
    final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(ModMain.MODID + ".toml"))
        .sync()
        .autosave()
        .writingMode(WritingMode.REPLACE)
        .build();
    configData.load();
    COMMON_CONFIG.setConfig(configData);
  }
}

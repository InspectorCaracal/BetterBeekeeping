package com.clockworkcaracal.betterbees.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class BetterBeeConfig {
	public static ForgeConfigSpec CONFIG;

	public static ForgeConfigSpec.BooleanValue BEE_HIBERNATION;
	public static ForgeConfigSpec.BooleanValue FLAVORED_HONEY;

	static {
		ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();

		CONFIG_BUILDER.comment("Game settings").push("general");
		BEE_HIBERNATION = CONFIG_BUILDER.comment("Better Beekeeping adds the option for bees to hibernate in cold weather. Set to true to turn this feature on!")
				.define("beeHibernation", false);
		FLAVORED_HONEY = CONFIG_BUILDER.comment("Custom hives from Better Beekeeping can generate unique flavors of honey with minor additional effects. Set to false if you want to just use normal honey.")
				.define("flavoredHoney", true);

		CONFIG = CONFIG_BUILDER.build();
	}
}

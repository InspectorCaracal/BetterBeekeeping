package com.clockworkcaracal.betterbees.config;

import net.minecraftforge.common.ForgeConfigSpec;
import com.clockworkcaracal.betterbees.BetterBeekeeping;

final class BuildConfig {

	final ForgeConfigSpec.BooleanValue hibernate;
	final ForgeConfigSpec.BooleanValue flavoredHoney;

	BuildConfig(final ForgeConfigSpec.Builder builder) {
		builder.push("general");
		hibernate = builder
				.comment("Enable bees staying inside their hive when the weather is too cold.")
				.translation(BetterBeekeeping.MOD_ID + ".config.hibernate")
				.define("hibernate", true);
		flavoredHoney = builder
				.comment("Enable collecting flavored honey from crafted hives.")
				.translation(BetterBeekeeping.MOD_ID + ".config.flavoredHoney")
				.define("flavoredHoney", true);
		builder.pop();
	}

}
